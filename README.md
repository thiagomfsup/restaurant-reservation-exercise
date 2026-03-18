# Restaurant Reservation System – Training Exercise

## 🎯 Objective

Build a simple **Restaurant Reservation System** in Java, focusing on clean design, testing, and domain modeling.

This exercise is split into **two phases** and emphasizes:

* Test-Driven Development (TDD)
* Object-Oriented Design
* Separation of concerns
* In-memory data handling (no database)

---

## 🧱 Project Setup

### Requirements

* Java 17+ (or agreed version)
* Maven
* IDE of your choice

### Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/thiagomfsup/restaurant-reservation-exercise.git
   ```

2. Create a branch from:

   ```
   20260318_session
   ```

3. Work on your solution in your branch.

4. When finished:

    * Push your branch
    * Open a Pull Request (PR) targeting:

      ```
      20260318_session
      ```

5. The most accepted PR will be merged 🎉

---

## 🧩 Functional Requirements

### Phase 1: Restaurant Reservation.Reservation

Implement functionality to:

* Add a restaurant
* Delete a restaurant
* Modify a restaurant (optional / extra)

Each restaurant should include:

* Unique ID
* Name
* Capacity (number of people)
* Opening hours (per day or simplified model)
* Closing days (days when the restaurant is closed)

---

### Phase 2: Reservation Reservation

Implement functionality to:

* Add a reservation
* Delete a reservation
* Modify a reservation

Each reservation must include:

* Unique Reservation ID (UUID generated)
* Reference to a Restaurant.Restaurant ID
* Customer name
* Customer phone number
* Party size
* Reservation date and time
* Fixed duration of **2 hours**

---

## ⚠️ Validation Rules

### General

* Reservation must not be null
* Restaurant must exist

---

### Date & Time Constraints

* Reservation **cannot be made on days when the restaurant is closed**
* Reservation must respect opening hours:

    * Cannot start before opening time
    * Cannot end after closing time
* Duration is fixed:

    * `end_time = start_time + 2 hours`

---

### Capacity Constraints

* The total number of people in **overlapping reservations** must not exceed restaurant capacity

#### Overlapping Definition

Two reservations overlap if their time intervals intersect.

Example:

```
Reservation A: 18:00 - 20:00
Reservation B: 19:00 - 21:00 → OVERLAP
```

Constraint:

```
sum(party sizes of overlapping reservations) + new reservation ≤ capacity
```

---

## 🧪 Technical Constraints

* Use **pure Java** (no frameworks like Spring)
* Use **Maven**
* No database allowed

### Persistence

* Define repository interfaces
* Provide an **in-memory implementation**

---

## ✅ Testing Requirements

* Follow **Test-Driven Development (TDD)**
* All functionality must be covered by **unit tests**
* Focus on:

    * Business rules
    * Edge cases
    * Validation logic

---

## 💡 Suggested Design Guidelines

* Use clear domain models:

    * `Restaurant`
    * `Reservation`
* Separate:

    * Domain logic
    * Persistence
    * Application logic
* Prefer immutability where possible
* Keep logic testable and isolated

---

## 📦 Deliverables

* Clean, working code
* Passing unit tests
* Readable structure
* Pull Request ready for review

---

## 🚀 Bonus (Optional)

* Implement restaurant modification
* Improve validation error handling
* Add meaningful domain exceptions
* Handle time using `java.time` API properly

---

Good luck and have fun! 🎯
