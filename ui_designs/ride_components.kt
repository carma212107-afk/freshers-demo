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
fun RideCard(
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
		modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(17.dp))
			.background(Colours.LightModeBackground2, RoundedCornerShape(17.dp)).clickable(onClick = onClick)
			.padding(horizontal = 12.dp, vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Text(startCity, color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Text("  ->  ", color = Colours.Accent, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
			Text(endCity, color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		}
		if (searchResult) {
			Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
				CalendarIcon(Modifier.size(14.dp))
				Text(date, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				ClockIcon(Modifier.size(14.dp))
				Text(time, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				Spacer(Modifier.weight(1f))
				Text(price, color = Colours.LightModeText, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.size)
				Text("pp", color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
			}
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			ProfilePic(initials = driverInitials.ifBlank { driverName.take(2).uppercase() }) // is the ifBlank() necessary?
			Spacer(Modifier.width(6.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				if (searchResult) {
					Text(driverName, color = Colours.LightModeText, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.size)
					if (driverUniversity.isNotBlank()) Text(driverUniversity, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					if (driverCourse.isNotBlank()) Text("${driverYear} - ${driverCourse}", color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				} else {
					Row(verticalAlignment = Alignment.CenterLeft) {
						Text(driverName, color = Colours.LightModeText, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.size)
						if (upcoming) Text(" - ${driverUniversity}", color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					}
					if (upcoming) Text(carDescription, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					else Text(driverUniversity, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
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
					Text(date, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
					ClockIcon(Modifier.size(14.dp))
					Text(time, color = Colours.LightModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.size)
				}
			}
			Text(filterLabel, modifier = Modifier.padding(start = 6.dp).background(Colours.LightModeSecondary, RoundedCornerShape(15.dp)).padding(horizontal = 10.dp, vertical = 4.dp), color = Colours.LightModeText, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.size)
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
				startCity = ride.startCity,
				endCity = ride.endCity,
				date = ride.getFormattedDate(),
				time = ride.getFormattedTime(),
				upcoming = ride.isUpcoming(),
				price = ride.getPricePerSeat(),
				driver = ride.driver,
				driverName = driver.getFormattedFirstName(),
				driverUniversity = driver.university,
				driverYear = driver.getFormattedUniYear(),
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
		modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(15.dp))
			.background(Colours.LightModeBackground2, RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 10.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column(modifier = Modifier.width(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
			RouteMarker()
			Box(Modifier.width(1.dp).height(31.dp).background(Colours.LightModePrimary))
			RouteMarker(arrival = true)
		}
		val textKeyInfo = if (edit) TextFormatting.Boxes1 else TextFormatting.Text2
		val textLabels = if (edit) TextFormatting.Boxes2 else TextFormatting.Text3
		Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
			Text("Departing from", color = Colours.LightModeText, fontSize = textLabels.size, fontWeight = textLabels.weight)
			Text(ride.startCity, color = Colours.LightModeText, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
			Spacer(Modifier.height(12.dp))
			Text("Arriving at", color = Colours.LightModeText, fontSize = textLabels.size, fontWeight = textLabels.weight)
			Text(ride.endCity, color = Colours.LightModeText, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
		}
		Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.SpaceBetween) {
			if (edit) {
				Spacer(Modifier.height(12.dp)) // probably need a bigger spacer ~ 36.dp?
				Text("Est. duration", color = Colours.LightModeText, fontSize = textLabels.size, fontWeight = textLabels.weight)
				Text(ride.calculateDuration(), color = Colours.LightModeText, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
			} else {
				Text("Departure time", color = Colours.LightModeText, fontSize = textLabels.size, fontWeight = textLabels.weight)
				Text(ride.getFormattedTime(), color = Colours.LightModeText, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
				Spacer(Modifier.height(12.dp))
				Text("Est. arrival", color = Colours.LightModeText, fontSize = textLabels.size, fontWeight = textLabels.weight)
				Text(ride.calculateArrivalTime(), color = Colours.LightModeText, fontSize = textKeyInfo.size, fontWeight = textKeyInfo.size)
			}
		}
	}
}

@Composable
private fun RouteMarker(modifier: Modifier = Modifier, arrival: Boolean = false) { // from/to marker
    colour = if (arrival) Colours.LightModePrimary else Colours.Accent
	Canvas(modifier.size(15.dp)) {
		drawCircle(colour, radius = size.minDimension / 2f)
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
        date = ride.getFormattedDate(),
        time = ride.getFormattedTime(),
        price = "",
        driverName = driver.getFormattedFirstName(),
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
		Text("Cost breakdown", color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Column(modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(17.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
			CostRow("Fuel cost", breakdown[0])
			CostRow("Split between ${ride.getNoOfBookedSeats()} passengers", breakdown[1]) // how to shortly say "passengers and driver" ?
			CostRow("Carma fee (10%)", breakdown[2])
			Box(Modifier.fillMaxWidth().height(1.dp).background(Colours.Accent)) // separator line
			CostRow("Your total", breakdown[3], total = true)
		}
	}
}
@Composable
private fun CostRow(label: String, value: String, total: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(label, color = Colours.LightModeText, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
		Text(value, color = Colours.LightModeText, fontSize = if (total) TextFormatting.Text2.size else TextFormatting.Text3.size, fontWeight = if (total) TextFormatting.Text2.weight else TextFormatting.Text3.weight)
	}
}


// ride info blocks (confirmation pages)

@Composable
fun ViewRideSummary(
    ride: Ride
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(17.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
		Text("${ride.startCity}  ->  ${ride.endCity}", color = Colours.LightModeText, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			RideInfoBox(ride.getFormattedDate(), "date", Modifier.weight(1f))
			RideInfoBox(ride.getFormattedTime(), "departure time", Modifier.weight(1f))
			RideInfoBox(ride.noOfFreeSeats, "seats available", Modifier.weight(1f))
		}
	}
}
@Composable
private fun RideInfoBox(value: String, label: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Colours.LightModeSecondary, RoundedCornerShape(4.dp)).padding(vertical = 7.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.LightModeText, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Text(label, color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
	}
}


// individual chat page referencing ride block
@Composable
fun DiscussingRideBanner(ride: Ride, modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxWidth().background(Colours.DarkModePrimary, RoundedCornerShape(10.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
		Text("DISCUSSING THIS RIDE", color = Colours.DarkModeText, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
		Text("${ride.startCity}  ->  ${ride.endCity}", color = Colours.DarkModeText, fontSize = TextFormatting.Text2.size, fontWeight = TextFormatting.Text2.weight)
		Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("${ride.getFormattedDate()}", color = Colours.DarkModeText, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
            Text("${ride.getFormattedTime()}", color = Colours.DarkModeText, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
            Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                "${ride.getPricePerSeat()}", color = Colours.DarkModeText, fontSize = TextFormatting.SmallText1.size, fontWeight = TextFormatting.SmallText1.weight)
                " per seat", color = Colours.DarkModeText, fontSize = TextFormatting.SmallText2.size, fontWeight = TextFormatting.SmallText2.weight)
            }
        }
	}
}


// Seat icons and no_of_free_seats indicator
@Composable
fun NoOfFreeSeatsIndicator(modifier: Modifier = Modifier, noOfFreeSeats: Int = 3, noOfSeats: Int = 4) {
	Row(modifier = modifier.padding(horizontal = 2.dp), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
		for (i in 1..noOfSeats) {
            IndividualSeatIcon(available = i <= noOfFreeSeats)
        }
	}
}
@Composable
fun IndividualSeatIcon(available: Boolean = false, modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.size(10.dp).border(1.dp, Colours.LightModePrimary, RoundedCornerShape(3.dp))
			.background(if (available) Colours.LightModeBackground1 else Colours.LightModePrimary, RoundedCornerShape(3.dp)),
	)
}