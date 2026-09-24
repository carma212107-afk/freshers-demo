class User(
    val userID: UUID,
    val pronouns: String,
    val aboutMe: String = "",
    val socialProfiles: Map<SocialProfile, String>,
    val verifiedStudent: Boolean = false,
    val verifiedDriver: Boolean = false,
    val hometown: String,
    val university: String,
    val uniCourse: String,
    val rating: Double, // to 1d.p.
    val rides: List<Ride>,
    val reviews: List<Review>,
    val carbonSaved: Float,
    private val firstName: String,
    private val lastName: String,
    private val profilePicURL: String = null,
    private val uniYear: Int = 1,
    object DefaultSearchPreferences {
        val female_only: Boolean = false,
        val quiet_car: QuietCarPreference = QuietCarPreference.None,
        val pets_welcome: Boolean = false,
        val smokers: Boolean = false,
        val front_seat: Boolean = false,
        val extra_luggage: Boolean = false,
    }
) {
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    fun getInitials(): String {
        return "${firstName.first()}${lastName.first()}"
    }

    fun profilePicComponent(theme: Theme = Theme.Light, unread: Boolean = false, modifier: Modifier = Modifier) {
        val large = theme == Theme.Dark && !unread
        val diameter = if (large) 75.dp else 30.dp
        val light = theme == Theme.Light
        val scheme = ColourScheme(
            background = if (light) Colours.LightMode.Secondary else Colours.Accent,
            border = if (light) Colours.LightMode.Border else Colours.DarkMode.Secondary,
            text = if (light) Colours.LightMode.Text else Colours.DarkMode.Secondary
        )

        if profilePicUrl != null { // how to display image in specific shape ?
            AsyncImage(
                model = profilePicUrl,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(diameter)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(modifier = modifier.size(diameter), contentAlignment = Alignment.Center) {
                Canvas(Modifier.matchParentSize()) {
                    drawCircle(scheme.background, radius = size.minDimension / 2f)
                    if (!large) drawCircle(scheme.border, radius = size.minDimension / 2f, style = Stroke(1f))
                }
                Text(
                    getInitials(),
                    color = scheme.text,
                    fontSize = if (large) TextFormatting.Heading2.size else TextFormatting.Boxes1.size,
                    fontWeight = if (large) TextFormatting.Heading2.weight else TextFormatting.Boxes1.weight,
                )
            }
        }
    }

    fun formatFirstName(): String {
        return "$firstName ${lastName.first()}."
    }

    fun formatUniYear(): String {
        return when (uniYear) {
            1 -> "1st Year"
            2 -> "2nd Year"
            3 -> "3rd Year"
            else -> "${uniYear}th Year"
        }
    }

    fun calculateNoOfRides(): Int { return rides.size }

    fun getRating(): String {
        return String.format("%.1f", rating)
    }
    fun calculatePositiveRatingPercentage(): String {
        val positiveRating = (rating / 5f * 100).toInt() // currently converting 5star rating to percentage
        // should be changed to percentage of positive reviews once review system is implemented
        return "$positiveRating%"
    }

    fun calculateNoTreesPlanted(): Int {
        val treesPlanted = (carbonSaved / 23.3f).toInt() // 1 tree absorbs 23.3kg CO2 per year
        return treesPlanted
    }
}

class Login() {}

enum class QuietCarPreference { Quiet, None, Loud }
enum class Filter { FemaleOnly, Quiet, PetsWelcome, Smokers, FrontSeat, ExtraLuggage }

enum class FuelType = { Petrol, Diesel, Electric, Hybrid }
class Car(
    val carID: UUID,
    val driver: UserProfile,
    const val carReg: String,
    const val makeModel: List<String>,
    const val fuelType: FuelType,
    val noOfSeats: Int = 3,
    val mpg: Double, // get miles per gallon from website
) {
    fun getCarDescription(hidden: Boolean = true): String {
        description = "${makeModel[0]} ${makeModel[1]}"
        if (hidden) { return description }
        // hidden=false only within 24hrs of departure time
        else { return description + " - ${carReg}" }
    }
}

data class PassengerInfo(
    val passengerID: User,
    val extraSeats: Map<Filter, Int>, // may need more than one seat for extra luggage, pet
    val frontSeat: Boolean = false,
)
class Ride(
    val rideID: UUID,
    val driver: User,
    val car: Car,
    val startCity: String,
    val detours: List<Detour>
    val endCity: String,
    val departureDateTime: LocalDateTime,
    val noOfSeats: Int = car.noOfSeats, // get from Car info
    val passengers: List<PassengerInfo>,
    val filters: List<Filter>,
    val carbonSaved: Int, // in kg
) {
    fun isUpcoming(): Boolean {
        return departureDateTime < DateTime.now()
    }
    fun formatDate(departure: Boolean = true): String {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
    fun formatTime(departure: Boolean = true): String {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
    fun calculateDuration(): String { return "" }
    fun calculateArrivalTime() : String {
        return (formatTime().toDateTime() + calculateDuration().toDateTime()).toString() // not functional as strings
    }

    fun getNoOfBookedSeats(): Int {
        bookedSeats = passengers.size
        for p in passengers {
            bookedSeats += p.extraSeats.value
        }
        return bookedSeats
    }
    fun getNoOfFreeSeats(): Int {
        return noOfSeats - getNoOfBookedSeats()
    }

    fun getCarDescription(): String { car.getCarDescription() }

    private fun roundDP(num: Double, decP: Int = 2): Double {
        val formattedNum = String.format("%." + decP.toString() + "f", num).toDouble()
        return formattedNum
    }

    private fun calculateFuelCost(): Double {
        val fuelCost = 0.0
        // fuelCost = mpg * distance(mi) * price_per_gallon
        return roundDP(fuelCost)
    }
    private fun calculateIndividualFuelCost(sharedFuelCost: Double, shares: Int): Double {
        shares++ // driver still pays their own fuel share ?
        return roundDP(sharedFuelCost / shares)
    }
    private fun calculateCarmaFee(subtotal: Double): Double {
        return roundDP(subtotal * 0.1)
    }
    fun calculatePricePerSeat(approx: Boolean = true): String {
        val fuelCost = calculateFuelCost()
        val fuelShare = calculateIndividualFuelCost(fuelCost, passengers.size)
        val carmaFee = calculateCarmaFee(fuelShare)
        val total = fuelShare + carmaFee
        if (approx) return "≈£" + String.format("%.2f", pricePerSeat)
        else return "£" + String.format("%.2f", pricePerSeat)
    }
    fun getCostBreakdown(approx: Boolean = true): List<String> {
        poundString = when (approx) {
            true -> "≈£"
            false -> "£"
        }
        val fuelCost = poundString + calculateFuelCost().ToString()
        val fuelShare = poundString + calculateIndividualFuelCost(fuelCost, passengers.size).ToString()
        val carmaFee = poundString + calculateCarmaFee(fuelShare).ToString()
        val total = poundString + getPricePerSeat()
        val breakdown = listOf(fuelCost, fuelShare, carmaFee, total)
        return breakdown
    }
}


enum class DetourType = { Stop, Via }
data class Detour(
    val type: DetourType,
    val location: String,
) {}


class Review(
    val reviewID: UUID,
    val reviewer: User,
    val ride: Ride,
    val datePosted: Date = Date.now(),
    val quote: String,
    val rating: Double, // to 1d.p.
) {
    fun formatDate(): String {
        return datePosted.toString() // TODO: format date
    }
}