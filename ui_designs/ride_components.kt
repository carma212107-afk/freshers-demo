import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun RideCard(
	driver: User,
	startCity: String,
	endCity: String,
	date: String,
	time: String,
	upcoming: Boolean,
	price: String,
	driverName: String,
	driverUniversity: String = "",
	driverYear: String
	driverCourse: String = "",
	driverInitials: String = "",
	driverRating: String = "",
	noOfSeats: Int = 3
	seatsLeft: Int = 0,
	carDescription: String = "",
	filterLabel: String = "<filter>",
	onClick: () -> Unit = {},
	searchResult: Boolean = false,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(17.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(17.dp)).clickable(onClick = onClick)
			.padding(horizontal = 12.dp, vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Text(startCity, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Text("  ->  ", color = Colours.Accent, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Text(endCity, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		}
		if (searchResult) {
			Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
				CalendarIcon(Modifier.size(14.dp))
				Text(date, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				ClockIcon(Modifier.size(14.dp))
				Text(time, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				Spacer(Modifier.weight(1f))
				Text(price, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.size)
				Text("pp", color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
			}
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			driver.getProfilePic()
			Spacer(Modifier.width(6.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				if (searchResult) {
					Text(driverName, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.size)
					if (driverUniversity.isNotBlank()) Text(driverUniversity, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					if (driverCourse.isNotBlank()) Text("${driverYear} - ${driverCourse}", color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				} else {
					Row(verticalAlignment = Alignment.CenterLeft) {
						Text(driverName, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.size)
						if (upcoming) Text(" - ${driverUniversity}", color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					}
					if (upcoming) Text(carDescription, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					else Text(driverUniversity, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				}
			}
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				NumberRating(rating = driverRating) // needs Modifer argument
				if (upcoming) NoOfFreeSeatsIndicator(noOfFreeSeats = seatsLeft, noOfSeats = noOfSeats) // needs Modifer argument
			}
		}
		Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
			if (searchResult) {
				NoOfFreeSeatsIndicator(noOfFreeSeats = seatsLeft, noOfSeats = noOfSeats) // needs Modifer argument
			} else {
				Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
					CalendarIcon(Modifier.size(14.dp))
					Text(date, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					ClockIcon(Modifier.size(14.dp))
					Text(time, color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				}
			}
			Text(filterLabel, modifier = Modifier.padding(start = 6.dp).background(Colours.LightMode.Secondary, RoundedCornerShape(15.dp)).padding(horizontal = 10.dp, vertical = 4.dp), color = Colours.LightMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.size)
		}
	}
}

@Composable
fun RideList(
	rides: List<Ride>,
	searchResults: Boolean = false,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
		rides.forEach { ride ->
			RideCard(
				driver = ride.driver,
				startCity = ride.startCity,
				endCity = ride.endCity,
				date = ride.formatDate(),
				time = ride.formatTime(),
				upcoming = ride.isUpcoming(),
				price = ride.calculatePricePerSeat(),
				driver = ride.driver,
				driverName = driver.formatFirstName(),
				driverUniversity = driver.university,
				driverYear = driver.formatUniYear(),
				driverCourse = driver.course,
				driverInitials = driver.getInitials(),
				driverRating = driver.getRating(),
				noOfSeats = ride.noOfSeats,
				seatsLeft = ride.getNoOfFreeSeats(),
				carDescription = ride.getCarDescription(),
				filters = ride.filters,
				onClick = ride.onClick,
				searchResult = searchResults,
			)
		}
	}
}


// 'from A to B' route block components

@Composable
fun RideRoute(
	edit: Boolean = true,
	ride: Ride,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column(modifier = Modifier.width(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
			RouteMarker(colour = Colours.Accent)
			RouteMarker(colour = Colours.LightMode.Primary, line = true)
			RouteMarker(colour = Colours.LightMode.Primary)
		}
		val textKeyInfo = if (edit) TextFormatting.Boxes1 else TextFormatting.Text2
		val textLabels = if (edit) TextFormatting.Boxes2 else TextFormatting.Text3
		Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
			Text("Departing from", color = Colours.LightMode.Text, fontSize = textLabels.size, fontWeight = textLabels.weight)
			Text(ride.startCity, color = Colours.LightMode.Text, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
			Spacer(Modifier.height(12.dp))
			Text("Arriving at", color = Colours.LightMode.Text, fontSize = textLabels.size, fontWeight = textLabels.weight)
			Text(ride.endCity, color = Colours.LightMode.Text, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
		}
		Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.SpaceBetween) {
			if (edit) {
				Spacer(Modifier.height(12.dp)) // probably need a bigger spacer ~ 36.dp?
				Text("Est. duration", color = Colours.LightMode.Text, fontSize = textLabels.size, fontWeight = textLabels.weight)
				Text(ride.calculateDuration(), color = Colours.LightMode.Text, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
			} else {
				Text("Departure time", color = Colours.LightMode.Text, fontSize = textLabels.size, fontWeight = textLabels.weight)
				Text(ride.formatTime(), color = Colours.LightMode.Text, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
				Spacer(Modifier.height(12.dp))
				Text("Est. arrival", color = Colours.LightMode.Text, fontSize = textLabels.size, fontWeight = textLabels.weight)
				Text(ride.calculateArrivalTime(), color = Colours.LightMode.Text, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
			}
		}
	}
}

@Composable
fun RouteMarker(modifier: Modifier = Modifier, colour: Color = Colours.Accent, line: Boolean = false) { // from/to marker
	if (line) {
		Box(Modifier.width(1.dp).fillMaxHeight().background(colour).padding(end = 1.dp))
	} else {
		Canvas(modifier.size(15.dp)) {
			drawCircle(colour, radius = size.minDimension / 2f)
		}
	}
}


// purpose TBD ???

@Composable
fun PostRidePreview(
    ride: Ride
    driver: User = ride.driver
	modifier: Modifier = Modifier,
) {
	RideCard(
        startCity = ride.startCity,
        endCity = ride.endCity,
        date = ride.formatDate(),
        time = ride.formatTime(),
        price = "",
        driverName = driver.formatFirstName(),
        driverUniversity = driver.university,
        carDescription = ride.car.getCarDescription(),
        filterLabel = "",
    )
}


// cost breakdown components

@Composable
fun CostBreakdown(
    ride: Ride,
	modifier: Modifier = Modifier,
) {
    breakdown = ride.getCostBreakdown()
	Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
		Text("Cost breakdown", color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Column(modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(17.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
			CostRow("Fuel cost", breakdown[0])
			CostRow("Split between ${ride.getNoOfBookedSeats()} passengers", breakdown[1]) // how to shortly say "passengers and driver" ?
			CostRow("Carma fee (10%)", breakdown[2])
			Separator()
			CostRow("Your total", breakdown[3], total = true)
		}
	}
}
@Composable
private fun CostRow(label: String, value: String, total: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(label, color = Colours.LightMode.Text, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
		Text(value, color = Colours.LightMode.Text, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
	}
}


// ride info blocks (confirmation pages)

@Composable
fun ViewRideSummary(
    ride: Ride
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(17.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
		Text("${ride.startCity}  ->  ${ride.endCity}", color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			RideInfoBox(ride.formatDate(), "date", Modifier.weight(1f))
			RideInfoBox(ride.formatTime(), "departure time", Modifier.weight(1f))
			RideInfoBox(ride.noOfFreeSeats, "seats available", Modifier.weight(1f))
		}
	}
}
@Composable
fun RideInfoBox(value: String, label: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Colours.LightMode.Secondary, RoundedCornerShape(4.dp)).padding(vertical = 7.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.LightMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Text(label, color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
	}
}


// individual chat page referencing ride block
@Composable
fun DiscussingRideBanner(ride: Ride, modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxWidth().background(Colours.DarkMode.Background2, RoundedCornerShape(10.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
		Text("DISCUSSING THIS RIDE", color = Colours.DarkMode.Text, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
		Text("${ride.startCity}  ->  ${ride.endCity}", color = Colours.DarkMode.Text, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("${ride.formatDate()}", color = Colours.DarkMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
            Text("${ride.formatTime()}", color = Colours.DarkMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
            Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                "${ride.calculatePricePerSeat()}", color = Colours.DarkMode.Text, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
                " per seat", color = Colours.DarkMode.Text, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.weight)
            }
        }
	}
}


// Seat icons and no_of_free_seats indicator
@Composable
private fun NoOfFreeSeatsIndicator(modifier: Modifier = Modifier, noOfFreeSeats: Int = 3, noOfSeats: Int = 4) {
	Row(modifier = modifier.padding(horizontal = 2.dp), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
		for (i in 1..noOfSeats) {
            IndividualSeatIcon(available = i <= noOfFreeSeats)
        }
	}
}
@Composable
private fun IndividualSeatIcon(available: Boolean = false, modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.size(10.dp).border(1.dp, Colours.LightMode.Primary, RoundedCornerShape(3.dp))
			.background(if (available) Colours.LightMode.Background1 else Colours.LightMode.Primary, RoundedCornerShape(3.dp)),
	)
}


// Passenger list component (for driver to see passengers in their ride)

@Composable
fun PassengerList(ride: Ride, modifier: Modifier = Modifier, displayCarReg: Boolean = false)  {
	Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedEvenly) {
			Text("Your car", color = Colours.LightMode.Text, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
				NoOfFreeSeatsIndicator(ride.getNoOfFreeSeats(), ride.noOfSeats)
				Text("${ride.noOfBookedSeats()} / ${ride.noOfSeats} ${ride.car.makeModel[1]}", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
			}
		}
		if (displayCarReg) {
			Text("${ride.car.carReg} - ${ride.car.makeModel[0]} ${ride.car.makeModel[1]}", color = Colours.LightMode.Text, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
		}
		val displayedSeats = 0
		for p in ride.passengers {
			PassengerProfileCard(passenger = p.passengerID, frontSeat = p.frontSeat, onClick = onPassengerClick)
			if (p.extraSeats.size > 0) {
				for extraSeat in p.extraSeats {
					PassengerExtraSlot(name = p.passengerID.formatFirstName(), reason = extraSeat.reason, onClick = onPassengerClick)
				}
			}
			displayedSeats += 1
		}
		for s in displayedSeats until (ride.noOfSeats + 1) { PassengerEmptySlot() }
	}
}
@Composable
private fun PassengerProfileCard(passenger: User, frontSeat: Boolean = false, onClick: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(20.dp))
			.clickable(onClick = onClick)
			.padding(10.dp),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			passenger.getProfilePic(theme = Theme.Light, modifier = Modifier.size(50.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				if (frontSeat) { nameText = "${passenger.formatFirstName()} (front seat)" }
				else { nameText = passenger.formatFirstName() }
				Text(
					nameText,
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Boxes1.size,
					fontWeight = TextFormatting.Boxes1.weight,
				)
				Text(
					"${passenger.university} · ${passenger.formatUniYear()}",
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Boxes2.size,
					fontWeight = TextFormatting.Boxes2.weight,
				)
			}
			NumberRating(passenger.getRating())
		}
		if (passenger.verifiedStudent) { VerificationTag("Verified student") }
	}
}
@Composable
private fun PassengerExtraSlot(name: String, reason: String, onClick: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(20.dp))
			.clickable(onClick = onClick)
			.padding(10.dp),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Text(
			name,
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Boxes1.size,
			fontWeight = TextFormatting.Boxes1.weight,
		)
		Text(
			"Extra seat for $reason",
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Boxes2.size,
			fontWeight = TextFormatting.Boxes2.weight,
		)
	}
}
@Composable
private fun EmptyPassengerSlot() {
	Box(
		modifier = Modifier.fillMaxWidth().height(25.dp).border(1.dp, Colours.LightMode.Border, RoundedCornerShape(20.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(20.dp)),
	)
}