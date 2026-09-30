import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ViewRidePage(
	ride: Ride? = null,
	modifier: Modifier = Modifier,
	onDriverClick: () -> Unit = {},
	onPassengerClick: () -> Unit = {},
	onJoinRide: () -> Unit = {},
	onLeaveRide: () -> Unit = {},
	onEditRide: () -> Unit = {},
	onCancelRide: () -> Unit = {},
	onHome: () -> Unit = {},
	onSearch: () -> Unit = {},
	onAddRide: () -> Unit = {},
	onMyRides: () -> Unit = {},
	onProfile: () -> Unit = {},
) {
	val driver = ride.driver
    val driverMode = driver == getCurrentUser() // if currentUser is the driver, show driver-relevant sections
	val seatsLeft = ride.getNoOfFreeSeats().toString()
    val seatsTotal = ride.noOfSeats
	val breakdown = ride.getCostBreakdown()
	val sectionTitle: String

	Column(modifier = modifier.fillMaxSize().background(Colours.LightMode.Background1)) {
		TopMenuBar(title = "Ride details")
        if (!driverMode) {
            // show dark mode driver profile overview
        }
		Column(
			modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 30.dp, vertical = 15.dp),
			verticalArrangement = Arrangement.spacedBy(24.dp),
		) {
			if (!driverMode) {
				Column(
					modifier = Modifier.fillMaxWidth().background(Colours.DarkMode.Background1).padding(horizontal = 15.dp).padding(bottom = 15.dp),
					verticalArrangement = Arrangement.spacedBy(5.dp),
				) {
					Text(
						"Your driver",
						color = Colours.DarkMode.Text,
						fontSize = TextFormatting.Heading2.size,
						fontWeight = TextFormatting.Heading2.weight,
					)
					DriverProfileCard(driver = driver, modifier = Modifier.fillMaxWidth(), onClick = onDriverClick)
				}
			}
			ViewRideSection("Journey") {
				RideRoute(edit = false, ride = ride)
			}
			ViewRideSection("Trip info") {
				Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
					RideInfoBox("125 mi", "distance", Modifier.weight(1f))
					RideInfoBox("$seatsLeft left", "seats available", Modifier.weight(1f))
				}
			}
			if (driverMode) { PassengerList(ride = ride) }
			else {
				ViewRideSection("Driver car") {
					CarDescription(ride.car, departure = ride.departureDateTime)
				}
			}
			if (driverMode) {sectiontitle = "Your preferences"}
			else {sectiontitle = "Driver preferences"}
			ViewRideSection(title) {
				ViewRideFilterGrid()
			}
			ViewRideSection("Notes for passengers") {
				Text(
					"Notes",
					modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.InputField.size,
					fontWeight = TextFormatting.InputField.weight,
				)
			}
			CostBreakdown(ride = ride)
			if (driverMode) {
				ContinueButtons("Edit ride", "Cancel ride", onEditRide, onCancelRide)
			} else {
				ContinueButtons("Join ride", "Leave ride", onJoinRide, onLeaveRide)
			}
		}
        BottomNavigationBar(onHome, onSearch, onAddRide, onMyRides, onProfile)
	}
}


@Composable
private fun DriverProfileCard(driver: User, modifier: Modifier = Modifier, onClick: () -> Unit) {
	Column(
		modifier = modifier.border(1.dp, Colours.DarkMode.Border, RoundedCornerShape(20.dp))
			.background(Colours.DarkMode.Background2, RoundedCornerShape(20.dp))
			.clickable(onClick = onClick)
			.padding(15.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
			driver.getProfilePic(theme = Theme.Dark, modifier = Modifier.size(75.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				Text(driver.formatFirstName(), color = Colours.DarkMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
				Text(
					driver.university,
					color = Colours.DarkMode.Text,
					fontSize = TextFormatting.Text3.size,
					fontWeight = TextFormatting.Text3.weight,
				)
				Text(
					"${driver.formatUniYear()} · ${driver.uniCourse}",
					color = Colours.DarkMode.Text,
					fontSize = TextFormatting.Text3.size,
					fontWeight = TextFormatting.Text3.weight,
				)
				Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
					if (driver.verifiedStudent) { VerificationTag("Verified student") }
					if (driver.verifiedDriver) { VerificationTag("Verified driver") }
				}
			}
		}
		DriverStats(driver)
	}
}

@Composable
private fun DriverStats(driver: User) {
	Row(
		modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			NumberRating(driver.getRating(), theme = Theme.Dark, format = TextFormatting.Text2)
			Text("rating", color = Colours.DarkMode.Text, fontSize = TextFormatting.Text3.size)
		}
		StatsDivider()
		StatValue(driver.calculateNoOfRides(), "rides")
		StatsDivider()
		StatValue("${driver.carbonSaved}kg", "saved CO2")
	}
}
@Composable
private fun StatsDivider() {
	Box(
		modifier = Modifier.padding(horizontal = 8.dp).width(1.dp).height(40.dp)
			.background(Colours.DarkMode.Border),
	)
}
@Composable
private fun StatValue(value: String, label: String) {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.DarkMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = FontWeight.Bold)
		Text(label, color = Colours.DarkMode.Text, fontSize = TextFormatting.Text3.size, textAlign = TextAlign.Center)
	}
}

@Composable
private fun VerificationTag(label: String) {
	Text(
		"✓ $label",
		modifier = Modifier.background(color = Colours.LightMode.Background2, RoundedCornerShape(20.dp)).padding(horizontal = 10.dp, vertical = 2.dp),
		color = Colours.LightMode.Text,
		fontSize = TextFormatting.SmallText2.size,
		fontWeight = TextFormatting.SmallText2.weight,
		maxLines = 1,
	)
}

@Composable
private fun ViewRideSection(title: String, content: @Composable () -> Unit) {
	Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Text(title, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		content()
	}
}

@Composable
private fun CarDescription(car: Car?, departure: DateTime) {
	val make = car?.makeModel[0]
	val model = car?.makeModel[1]

    // car reg hidden until 24hrs before departure
    // should the hidden be determined here, or in the car.getCarDescription() class method?
    val hidden = departure - now() = 24 // car reg hidden until 24hrs before departure


	Column(
		modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp)).background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 10.dp, vertical = 5.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
        if (hidden) {
            Text("$make $model", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
            Text("Car Reg. hidden until 24hrs before departure", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight, textAlign = TextAlign.Center)
	    } else {
            Text("${car.getCarDescription()}", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
            Text("$make $model", color = Colours.LightMode.Text, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ViewRideFilterGrid() {
	Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			ViewRideFilter("<filter>")
			ViewRideFilter("<filter>")
		}
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			ViewRideFilter("<filter>")
			ViewRideFilter("<filter>")
		}
	}
}
@Composable
private fun ViewRideFilter(label: String) {
	Text(label, modifier = Modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 3.dp), color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
}