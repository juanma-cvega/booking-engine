# Booking Engine - Domain Specification

## Overview

The Booking Engine is a facility management system that enables organizations (clubs) to manage physical spaces (buildings and rooms) and their time-based availability (slots) for member bookings. The system supports complex authorization rules, auction-based allocation for contested slots, and class scheduling with pre-reservations.

## Core Domain Concepts

### Club Management
**Purpose**: Organizations that own and manage facilities for their members.

- Clubs are created by admin users who become the initial administrators
- Users can request to join clubs via join requests
- Admins can accept or deny join requests
- Clubs manage authorization credentials for their members to access facilities

### Member Management
**Purpose**: Users who belong to clubs and can book facilities.

- Members are created when join requests are accepted
- Members receive authorization tags that determine which facilities they can access
- Members can book available slots in rooms they are authorized to use

### Building & Room Management
**Purpose**: Physical facility hierarchy and configuration.

- Buildings contain multiple rooms
- Rooms have operating hours and belong to a specific building and club
- Rooms can require authorization credentials for access
- Each room can have a slot lifecycle manager that controls slot state transitions

### Slot Management
**Purpose**: Time-based availability units that can be booked.

**Slot Lifecycle States**:
- **Created**: Initial state when slot is generated
- **Available**: Slot is open for booking by authorized members
- **Pre-Reserved**: Slot is temporarily held (e.g., during auction or for classes)
- **Reserved**: Slot is booked by a specific user

**Slot Rules**:
- Slots can only be reserved when in Available state
- Slots cannot be reserved after their start time has passed
- Slots can be made available again if released
- Slots are associated with a specific room, building, and club

### Booking Management
**Purpose**: User reservations of slots.

- Users create bookings for available slots they are authorized to access
- Only the booking owner can cancel their booking
- Bookings are time-stamped and linked to the user and slot
- Canceling a booking may make the slot available again

### Authorization Management
**Purpose**: Fine-grained access control for facilities.

**Authorization Levels**:
1. **Building-level**: Tags required to access any room in a building
2. **Room-level**: Tags required to access specific rooms
3. **Slot-type-level**: Different credentials for NORMAL vs EARLY_BIRD slots

**Authorization Rules**:
- If no credentials are configured, access is granted by default
- Members must have matching tags at all configured levels
- Tags are managed separately for clubs (what's required) and members (what they have)
- Authorization is checked at slot reservation time

### Auction Management
**Purpose**: Fair allocation of contested slots through bidding.

- Auctions are created for slots based on configuration
- Users can bid on slots during the auction period
- Auction has a time window and considers bookings created within a specific timeframe
- When auction finishes, a winner is determined based on bidding strategy
- Winner gets the slot pre-reserved or reserved

### Class Management
**Purpose**: Recurring scheduled activities that pre-reserve slots.

- Classes are created for buildings and can be assigned to rooms
- Classes have instructors who can be added or removed
- Classes can be registered to specific rooms
- Class timetables automatically pre-reserve slots for scheduled sessions
- Pre-reservations can be added or removed based on class schedules

### Slot Lifecycle Management
**Purpose**: Orchestration of slot state transitions and scheduling.

- Each room can have a lifecycle manager that controls slot behavior
- Manages slot creation scheduling (when next slots are generated)
- Handles pre-reservation logic for classes
- Manages auction configurations for rooms
- Controls slot state transitions based on time and events
- Coordinates between slot creation, availability, auctions, and bookings

## Key Business Flows

### User Booking Flow
1. User requests to join a club
2. Admin accepts the join request
3. Member is created with appropriate authorization tags
4. Room slots are created based on schedule
5. Slots transition to Available state
6. Member reserves an available slot (if authorized)
7. Booking is created and slot becomes Reserved

### Auction Flow
1. Slot is created for a room with auction configuration
2. Auction is started with time window
3. Multiple users add bids to the auction
4. Auction time expires
5. Winner is determined based on strategy
6. Winner's slot is reserved, others are notified

### Class Scheduling Flow
1. Admin creates a class for a building
2. Instructors are added to the class
3. Class is registered to specific rooms
4. Class timetable is configured
5. Slots are automatically pre-reserved for class sessions
6. Class participants can access pre-reserved slots

### Authorization Flow
1. Admin configures required tags for buildings/rooms
2. Admin assigns tags to club members
3. When member attempts to reserve a slot:
   - System checks building-level authorization
   - System checks room-level authorization
   - System checks slot-type authorization (NORMAL/EARLY_BIRD)
4. Access granted only if all configured levels match

## Domain Events

The system is event-driven with the following key events:

- **Club Events**: ClubCreated, JoinRequestCreated, JoinRequestAccepted, JoinRequestDenied
- **Member Events**: MemberCreated
- **Building Events**: BuildingCreated
- **Room Events**: RoomCreated
- **Slot Events**: SlotCreated, SlotMadeAvailable, SlotPreReserved, SlotReserved
- **Booking Events**: BookingCreated, BookingCanceled
- **Auction Events**: AuctionStarted, AuctionFinished, AuctionWinnerFound
- **Class Events**: ClassCreated, InstructorAdded, InstructorRemoved

These events enable loose coupling between components and support eventual consistency in the system.

## Domain Invariants

- A club must have at least one admin
- A room must belong to an existing building
- A slot can only be in one state at a time
- A booking can only be canceled by its owner
- Authorization tags must match at all configured levels for access
- Slots cannot be reserved after their start time
- Auctions must have a defined time window and winner selection strategy

## Future Considerations

Based on TODOs in the codebase:
- Should Member entity contain role information to replace separate admin lists?
- Should JoinRequest be extracted as its own component with full lifecycle management?
