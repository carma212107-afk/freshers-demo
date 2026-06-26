"""
Carma Carpooling App — Domain Models
Generated from class diagram.
"""

from __future__ import annotations

import uuid
from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional


# ---------------------------------------------------------------------------
# Authentication
# ---------------------------------------------------------------------------

@dataclass
class Login:
    """Handles user authentication credentials."""

    user_id: uuid.UUID = field(default_factory=uuid.uuid4)
    email: str = ""
    password: str = ""          # Store hashed in production

    def verify(self) -> bool:
        """Verify login credentials. Returns True if valid."""
        return bool(self.email and self.password)

    def create(self) -> "Login":
        """Create and return a new Login record."""
        return Login(
            user_id=self.user_id,
            email=self.email,
            password=self.password,
        )


# ---------------------------------------------------------------------------
# Core User (base class)
# ---------------------------------------------------------------------------

@dataclass
class User:
    """Base user account shared by Drivers and Passengers."""

    user_id: uuid.UUID = field(default_factory=uuid.uuid4)
    name: str = ""
    email: str = ""
    password: str = ""          # Store hashed in production
    verifiedStudentEmail: bool = False
    hometown: str = ""
    university: str = ""
    year: int = 1               # Integer 1–5
    course: str = ""
    profile_pic: Optional[bytes] = None   # Raw image bytes
    rating: float = 0.0         # 0–5, 1 decimal place, represents average rating from other users
    no_of_ratings: int = 0      # Number of ratings received
    pronouns: str = ""          # e.g. "she/her"

    def create_account(self) -> None:
        """Persist a new user account."""
        print(f"[User] Account created for {self.name} ({self.email})")

    def change_details(self, **kwargs) -> None:
        """Update one or more profile fields."""
        for key, value in kwargs.items():
            if hasattr(self, key):
                setattr(self, key, value)
            else:
                raise AttributeError(f"User has no attribute '{key}'")

    def delete_account(self) -> None:
        """Delete the user account."""
        print(f"[User] Account deleted for {self.name}")

    def detectUniversity(self) -> str:

        # move dictionary outside of the method to avoid recreating it every time
        uniEmailDomains = {
            "uni.ac.uk": "University of Oxford",
            "cam.ac.uk": "University of Cambridge",
        }
        
        """Detect the university from the email domain."""
        for d in uniEmailDomains.keys():
            if d in self.email:
                return uniEmailDomains[d]
        return "Unknown University"

    def autoChangeUniYear(self) -> None:

        # 1. needs to be automatically triggered every September 1st,
        #           but for now we can call it manually in the test code
        # 2. does not currently account for placement years or repeating years

        """Automatically increment the year every September 1st based on the current date."""
        if (datetime.now().month == 9 and datetime.now().day == 1) and self.year < 5:
            self.year += 1
            print(f"[User] {self.name}'s year updated to {self.year}")

    def updateRating(self, new_rating: float) -> float:
        """Update the user's average rating based on a new rating received."""
        old_total = self.rating * self.no_of_ratings
        self.no_of_ratings += 1
        old_total += new_rating
        self.rating = old_total / self.no_of_ratings
        print(f"[User] {self.name}'s new average rating: {self.rating:.1f} based on {self.no_of_ratings} ratings")
        return self.rating


# ---------------------------------------------------------------------------
# Car (composed inside Driver)
# ---------------------------------------------------------------------------

@dataclass
class Car:
    """A vehicle registered to a driver."""

    driver_id: uuid.UUID = field(default_factory=uuid.uuid4)
    brand: str = ""
    car_reg: str = ""           # Registration plate
    num_seats: int = 4
    fuel_type: str = ""

    def verify(self) -> bool:
        """Verify the car registration details."""
        return bool(self.car_reg and self.brand)

    def create(self) -> "Car":
        """Create and return a new Car record."""
        return Car(
            driver_id=self.driver_id,
            brand=self.brand,
            car_reg=self.car_reg,
            num_seats=self.num_seats,
            fuel_type=self.fuel_type,
        )


# ---------------------------------------------------------------------------
# Driver (extends User, composes Car)
# ---------------------------------------------------------------------------

@dataclass
class Driver(User):
    """A user who can offer rides. Owns a Car."""

    num_rides: int = 0
    co2_saved_kg: int = 0
    verifiedDriversLicence: bool = False
    car: Optional[Car] = None   # Composition: Driver "has" a Car

    def create_ride(
        self,
        seat_count: int = Car.num_seats - 1,   # Default to max passengers based on car seats
    ) -> "Ride":
        """Create a new Ride offered by this driver."""
        ride = Ride(
            driver_id=self.user_id,
            num_free_seats=seat_count,
        )
        self.num_rides += 1
        print(f"[Driver] {self.name} created ride {ride.ride_id}")
        return ride

    def change_details(self, **kwargs) -> None:
        """Update driver-specific or inherited profile fields."""
        super().change_details(**kwargs)

    def cancel_ride(self, ride: "Ride") -> "Cancellation":
        """Cancel an existing ride and return a Cancellation record."""
        cancellation = Cancellation(ride_id=ride.ride_id)
        print(f"[Driver] {self.name} cancelled ride {ride.ride_id}")
        return cancellation


# ---------------------------------------------------------------------------
# Passenger (extends User)
# ---------------------------------------------------------------------------

@dataclass
class Passenger(User):
    """A user who can join rides offered by drivers."""

    prefer_female_only: bool = False
    prefer_quiet_car: bool = False

    def join_ride(self, ride: "Ride") -> bool:
        """Attempt to join a ride. Returns True on success."""
        if ride.num_free_seats <= 0:
            print(f"[Passenger] No free seats on ride {ride.ride_id}")
            return False
        ride.seat_ids.append(self.user_id)
        ride.num_free_seats -= 1
        print(f"[Passenger] {self.name} joined ride {ride.ride_id}")
        return True

    def leave_ride(self, ride: "Ride") -> bool:
        """Leave a ride the passenger previously joined."""
        if self.user_id not in ride.seat_ids:
            print(f"[Passenger] {self.name} is not on ride {ride.ride_id}")
            return False
        ride.seat_ids.remove(self.user_id)
        ride.num_free_seats += 1
        print(f"[Passenger] {self.name} left ride {ride.ride_id}")
        return True


# ---------------------------------------------------------------------------
# Ride
# ---------------------------------------------------------------------------

@dataclass
class Ride:
    """A single carpooling journey created by a Driver."""

    ride_id: uuid.UUID = field(default_factory=uuid.uuid4)
    driver_id: Optional[uuid.UUID] = None
    seat_ids: list[Optional[uuid.UUID]] = field(default_factory=list)
    num_free_seats: int = 0

    def start_ride(self, driver: Driver) -> None:
        """Mark the ride as started (called by the Driver)."""
        print(f"[Ride] {driver.name} started ride {self.ride_id}")

    def end_ride(self, driver: Driver) -> None:
        """Mark the ride as completed (called by the Driver)."""
        print(f"[Ride] {driver.name} ended ride {self.ride_id}")

    def rate_passenger(self, driver: Driver, passenger: Passenger, rating: float) -> None:
        """Allow the Driver to rate a Passenger (0–5)."""
        if not (0 <= rating <= 5):
            raise ValueError("Rating must be between 0 and 5")
        print(
            f"[Ride] Driver {driver.name} rated passenger "
            f"{passenger.name}: {rating:.1f}"
        )

    def rate_driver(self, passenger: Passenger, driver: Driver, rating: float) -> None:
        """Allow a Passenger to rate the Driver (0–5)."""
        if not (0 <= rating <= 5):
            raise ValueError("Rating must be between 0 and 5")
        print(
            f"[Ride] Passenger {passenger.name} rated driver "
            f"{driver.name}: {rating:.1f}"
        )

    def notify_all_passengers(self, message: str) -> list["Notification"]:
        """Send a Notification to every passenger on this ride."""
        notifications: list[Notification] = []
        for passenger_id in self.seat_ids:
            if passenger_id is not None:
                notif = Notification(message=message)
                notif.create_notif()
                notif.send_notif()
                notifications.append(notif)
        return notifications


# ---------------------------------------------------------------------------
# Search
# ---------------------------------------------------------------------------

@dataclass
class Search:
    """Search filters used by a Passenger to find rides."""

    city_from: str = ""
    city_to: str = ""
    date: Optional[datetime] = None
    prefer_female_only: bool = False
    prefer_quiet: bool = False

    def change_filter(self, **kwargs) -> None:
        """Update one or more search filter fields."""
        for key, value in kwargs.items():
            if hasattr(self, key):
                setattr(self, key, value)
            else:
                raise AttributeError(f"Search has no filter '{key}'")

    def create_ride_alert(self, passenger_id: uuid.UUID) -> "RideAlert":
        """Create a RideAlert based on the current search filters."""
        alert = RideAlert(passenger_id=passenger_id, filters=self)
        print(f"[Search] Ride alert created for passenger {passenger_id}")
        return alert


# ---------------------------------------------------------------------------
# Ride Alert
# ---------------------------------------------------------------------------

@dataclass
class RideAlert:
    """
    Saves a Passenger's search filters and triggers a Notification
    automatically when a matching ride becomes available.
    """

    passenger_id: uuid.UUID = field(default_factory=uuid.uuid4)
    filters: Optional[Search] = None

    def delete_notif(self) -> None:
        """
        Automatically called when a ride matching the saved filters
        is booked; removes this alert.
        """
        print(
            f"[RideAlert] Alert for passenger {self.passenger_id} "
            "deleted — a matching ride was booked."
        )


# ---------------------------------------------------------------------------
# Notification
# ---------------------------------------------------------------------------

@dataclass
class Notification:
    """A notification message sent to a user."""

    notif_id: uuid.UUID = field(default_factory=uuid.uuid4)
    message: str = ""

    def create_notif(self) -> None:
        """Persist the notification."""
        print(f"[Notification] Created: {self.message!r}")

    def send_notif(self) -> None:
        """Dispatch the notification to the recipient."""
        print(f"[Notification] Sent ({self.notif_id}): {self.message!r}")

    def delete_notif(self) -> None:
        """Remove the notification."""
        print(f"[Notification] Deleted ({self.notif_id})")


# ---------------------------------------------------------------------------
# Cancellation
# ---------------------------------------------------------------------------

@dataclass
class Cancellation:
    """Records that a Ride has been cancelled."""

    ride_id: uuid.UUID = field(default_factory=uuid.uuid4)


# ---------------------------------------------------------------------------
# Quick smoke-test
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    # --- Create a driver with a car ---
    car = Car(brand="Toyota", car_reg="AB12 CDE", num_seats=4, fuel_type="Petrol")
    driver = Driver(name="Alice", email="alice@uni.ac.uk", year=3, course="CS", car=car)
    driver.create_account()

    # --- Create a passenger ---
    passenger = Passenger(name="Bob", email="bob@uni.ac.uk", year=2, course="Maths",
                          prefer_quiet_car=True)
    passenger.create_account()

    # --- Driver creates a ride ---
    ride = driver.create_ride()

    # --- Passenger searches and sets an alert ---
    search = Search(city_from="Oxford", city_to="London", prefer_quiet=True)
    alert = search.create_ride_alert(passenger_id=passenger.user_id)

    # --- Passenger joins the ride ---
    passenger.join_ride(ride)

    # --- Driver starts and ends the ride ---
    ride.start_ride(driver)
    ride.end_ride(driver)

    # --- Mutual ratings ---
    ride.rate_passenger(driver, passenger, rating=4.5)
    ride.rate_driver(passenger, driver, rating=5.0)

    # --- Ride sends a notification to all passengers ---
    ride.notify_all_passengers("Your ride to London has been completed. Thanks for riding!")

    # --- Alert auto-deletes when a matching ride is booked ---
    alert.delete_notif()

    # --- Driver cancels a future ride ---
    ride2 = driver.create_ride(seat_count=2)
    cancellation = driver.cancel_ride(ride2)
    print(f"Cancellation recorded for ride: {cancellation.ride_id}")