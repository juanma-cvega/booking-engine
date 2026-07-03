package com.jusoft.bookingengine.component.slot;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.SlotFixtures.OPEN_DATE;
import static com.jusoft.bookingengine.fixture.SlotFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixture.SlotFixtures.START_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyExistsException;
import com.jusoft.bookingengine.component.slot.api.SlotNotFoundException;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class SlotRepositoryInMemoryTest {

    private static final int POOL_SIZE = 2;
    private static final int RACE_REPETITIONS = 20;
    private static final int CRITICAL_SECTION_SLEEP_MILLIS = 400;
    private static final int AWAIT_AT_MOST_MILLIS = 2000;
    private static final int TERMINATION_TIMEOUT_SECONDS = 5;

    private final Clock clock = Clock.fixed(START_TIME.toInstant(), START_TIME.getZone());

    private Map<Long, Slot> store;
    private Lock lock;
    private SlotRepositoryInMemory slotRepository;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        store = new ConcurrentHashMap<>();
        lock = new ReentrantLock();
        slotRepository = new SlotRepositoryInMemory(store, lock, clock);
        executor = Executors.newFixedThreadPool(POOL_SIZE);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        executor.shutdownNow();
        executor.awaitTermination(TERMINATION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    @Test
    void execute_should_apply_transformation_and_persist_result() {
        Slot existingSlot = newSlot(CreatedSlotState.getInstance());
        store.put(SLOT_ID_1, existingSlot);

        Slot result = slotRepository.execute(SLOT_ID_1, Slot::makeAvailable);

        assertThat(result.getState()).isEqualTo(AvailableSlotState.getInstance());
        assertThat(slotRepository.find(SLOT_ID_1)).contains(result);
    }

    @Test
    void execute_should_throw_when_slot_not_found() {
        assertThatThrownBy(() -> slotRepository.execute(SLOT_ID_1, Slot::makeAvailable))
                .isInstanceOf(SlotNotFoundException.class)
                .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
    }

    @Test
    void save_should_store_a_new_slot() {
        Slot newSlot = newSlot(CreatedSlotState.getInstance());

        slotRepository.save(newSlot);

        assertThat(slotRepository.find(SLOT_ID_1)).contains(newSlot);
    }

    @Test
    void save_should_reject_an_existing_id() {
        Slot existingSlot = newSlot(CreatedSlotState.getInstance());
        store.put(SLOT_ID_1, existingSlot);
        Slot anotherSlotWithSameId = newSlot(AvailableSlotState.getInstance());

        assertThatThrownBy(() -> slotRepository.save(anotherSlotWithSameId))
                .isInstanceOf(SlotAlreadyExistsException.class);
    }

    @RepeatedTest(RACE_REPETITIONS)
    void concurrent_save_should_not_clobber_an_in_flight_execute()
            throws ExecutionException, InterruptedException {
        Slot existingSlot = newSlot(CreatedSlotState.getInstance());
        store.put(SLOT_ID_1, existingSlot);

        TrackingOperator trackingOperator = new TrackingOperator();
        Future<Slot> executeTask =
                executor.submit(() -> slotRepository.execute(SLOT_ID_1, trackingOperator));

        await().atMost(AWAIT_AT_MOST_MILLIS, TimeUnit.MILLISECONDS)
                .until(() -> trackingOperator.isRunning);

        Slot competingSlot = newSlot(AvailableSlotState.getInstance());
        Future<Void> saveTask =
                executor.submit(
                        () -> {
                            slotRepository.save(competingSlot);
                            return null;
                        });

        Slot executeResult = executeTask.get();
        assertThatThrownBy(saveTask::get)
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(SlotAlreadyExistsException.class);
        assertThat(slotRepository.find(SLOT_ID_1)).contains(executeResult);
    }

    private Slot newSlot(SlotState state) {
        return new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, state);
    }

    private static class TrackingOperator implements UnaryOperator<Slot> {

        private volatile boolean isRunning;

        @Override
        public Slot apply(Slot slot) {
            isRunning = true;
            sleep(CRITICAL_SECTION_SLEEP_MILLIS);
            Slot transformed = slot.makeAvailable();
            isRunning = false;
            return transformed;
        }

        private static void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
