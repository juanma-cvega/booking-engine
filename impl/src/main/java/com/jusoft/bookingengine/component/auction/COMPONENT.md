# Auction Component

Manages a time-limited bidding process for contested slots. When a slot enters auction,
users bid; the winner gets the slot pre-reserved.

## Invariants
- An auction has a fixed open window computed at creation: `startTime` to `startTime + auctionDuration`.
- Bids are rejected after the auction closes (`AuctionFinishedException`).
- A user may bid multiple times — deduplication by `userId` equality (via `Bid.equals` which excludes `creationTime`).
- `referenceId` is the slot id the auction is for — used to look up the auction from outside.
- Winner selection is delegated to an `AuctionWinnerStrategy` (strategy pattern, pluggable).
- If no winner is found, `AuctionUnsuccessfulEvent` is published and the slot is released.

## Events published
| Event | When |
|---|---|
| `AuctionStartedEvent` | Auction started (carries `referenceId` = slotId, `openDate`) |
| `AuctionWinnerFoundEvent` | Winner selected (carries `auctionWinnerId`, `slotId`) |
| `AuctionUnsuccessfulEvent` | No bidders or no winner found |
| `AuctionFinishedEvent` | Auction finished |

## Dependencies
- `strategy.auctionwinner` — `AuctionWinnerStrategy` and `AuctionConfigInfo`
- `timer.OpenDate` — auction window
- `MessagePublisher` — event bus

