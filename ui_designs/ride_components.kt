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
	price: String,
	driverName: String,
	driverUniversity: String = "",
	driverYear: String = "",
	driverInitials: String = "",
	driverRating: String = "",
	seatsLeft: Int? = null,
	carDescription: String = "",
	filterLabel: String = "<filter>",
	onClick: () -> Unit = {},
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightModePrimary, RoundedCornerShape(17.dp))
			.background(Colours.LightModeBackground2, RoundedCornerShape(17.dp)).clickable(onClick = onClick)
			.padding(horizontal = 12.dp, vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Text("$startCity  ", color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
			Text("->", color = Colours.Accent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
			Text("  $endCity", color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
			Spacer(Modifier.weight(1f))
			Text(price, color = Colours.LightModeText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
		}
		Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
			CalendarIcon(Modifier.size(14.dp))
			Text(date, color = Colours.LightModeText, fontSize = 12.sp)
			ClockIcon(Modifier.size(14.dp))
			Text(time, color = Colours.LightModeText, fontSize = 12.sp)
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			ProfilePic(initials = driverInitials.ifBlank { driverName.take(2).uppercase() })
			Spacer(Modifier.width(6.dp))
			Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
				Text(driverName, color = Colours.LightModeText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
				if (driverUniversity.isNotBlank()) Text(driverUniversity, color = Colours.LightModeText, fontSize = 11.sp)
				if (driverYear.isNotBlank()) Text(driverYear, color = Colours.LightModeText, fontSize = 11.sp)
			}
			if (driverRating.isNotBlank()) Text("* $driverRating", color = Colours.LightModeText, fontSize = 12.sp)
		}
		Row(verticalAlignment = Alignment.CenterVertically) {
			if (carDescription.isNotBlank()) Text(carDescription, color = Colours.LightModeText, fontSize = 11.sp, modifier = Modifier.weight(1f))
			else Spacer(Modifier.weight(1f))
			if (seatsLeft != null) Text("$seatsLeft seats left", color = Colours.LightModeText, fontSize = 11.sp)
			Text(filterLabel, modifier = Modifier.padding(start = 6.dp).background(Colours.LightModeSecondary, RoundedCornerShape(15.dp)).padding(horizontal = 10.dp, vertical = 4.dp), color = Colours.LightModeText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
		}
	}
}

@Composable
fun RideList(
	rides: List<RideCardData>,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
		rides.forEach { ride ->
			RideCard(
				startCity = ride.startCity,
				endCity = ride.endCity,
				date = ride.date,
				time = ride.time,
				price = ride.price,
				driverName = ride.driverName,
				driverUniversity = ride.driverUniversity,
				driverYear = ride.driverYear,
				driverInitials = ride.driverInitials,
				driverRating = ride.driverRating,
				seatsLeft = ride.seatsLeft,
				carDescription = ride.carDescription,
				filterLabel = ride.filterLabel,
				onClick = ride.onClick,
			)
		}
	}
}

data class RideCardData(
	val startCity: String,
	val endCity: String,
	val date: String,
	val time: String,
	val price: String,
	val driverName: String,
	val driverUniversity: String = "",
	val driverYear: String = "",
	val driverInitials: String = "",
	val driverRating: String = "",
	val seatsLeft: Int? = null,
	val carDescription: String = "",
	val filterLabel: String = "<filter>",
	val onClick: () -> Unit = {},
)

@Composable
fun RideRoute(
	edit: Boolean = true,
	startCity: String = "<startC>",
	endCity: String = "<endC>",
	startTime: String = "HH:mm",
	endTime: String = "HH:mm",
	duration: String = "Est. duration",
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
			RouteMarker()
		}
		Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
			Text("Departing from", color = Colours.LightModeText, fontSize = if (edit) 14.sp else 15.sp)
			Text(startCity, color = Colours.LightModeText, fontSize = if (edit) 15.sp else 20.sp, fontWeight = FontWeight.Bold)
			Spacer(Modifier.height(12.dp))
			Text("Arriving at", color = Colours.LightModeText, fontSize = if (edit) 14.sp else 15.sp)
			Text(endCity, color = Colours.LightModeText, fontSize = if (edit) 15.sp else 20.sp, fontWeight = FontWeight.Bold)
		}
		Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.SpaceBetween) {
			if (edit) {
				Text("Time", color = Colours.LightModeText, fontSize = 15.sp)
				Text(startTime, color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
				Spacer(Modifier.height(12.dp))
				Text(duration, color = Colours.LightModeText, fontSize = 14.sp)
				Text(endTime, color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
			} else {
				Text(startTime, color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
				Text("Time", color = Colours.LightModeText, fontSize = 15.sp)
				Spacer(Modifier.height(12.dp))
				Text(endTime, color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
				Text("Time", color = Colours.LightModeText, fontSize = 15.sp)
			}
		}
	}
}

@Composable
private fun RouteMarker(modifier: Modifier = Modifier) {
	Canvas(modifier.size(15.dp)) {
		drawCircle(Colours.LightModePrimary, radius = size.minDimension / 2f)
	}
}

@Composable
fun PostRidePreview(
	startCity: String,
	endCity: String,
	driverName: String,
	driverUniversity: String,
	driverCar: String,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
		Text("Post Ride", color = Colours.LightModePrimary, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		RideCard(
			startCity = startCity,
			endCity = endCity,
			date = "<date>",
			time = "HH:mm",
			price = "",
			driverName = driverName,
			driverUniversity = driverUniversity,
			carDescription = driverCar,
			filterLabel = "",
		)
	}
}

@Composable
fun CostBreakdown(
	fuelCost: String,
	splitCost: String,
	carmaFee: String,
	total: String,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
		Text("View Ride", color = Colours.LightModePrimary, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Text("Cost breakdown", color = Colours.LightModePrimary, fontSize = TextFormatting.Heading2.size, fontWeight = TextFormatting.Heading2.weight)
		Column(modifier = Modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(17.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
			CostRow("Fuel cost (125mi x 0mi/gal)", fuelCost)
			CostRow("Split between 1 passengers", splitCost)
			CostRow("Carma fee (10%)", carmaFee)
			Box(Modifier.fillMaxWidth().height(1.dp).background(Colours.LightModeBorder))
			CostRow("Your total", total, bold = true)
		}
	}
}

@Composable
private fun CostRow(label: String, value: String, bold: Boolean = false) {
	Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
		Text(label, color = Colours.LightModeText, fontSize = 14.sp, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
		Text(value, color = Colours.LightModeText, fontSize = 14.sp, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
	}
}

@Composable
fun ViewRideSummary(
	startCity: String,
	endCity: String,
	date: String,
	departure: String,
	seatsAvailable: String,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier.fillMaxWidth().border(1.dp, Colours.LightModeBorder, RoundedCornerShape(17.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
		Text("$startCity  ->  $endCity", color = Colours.LightModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
		Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			RideInfoBox(date, "date", Modifier.weight(1f))
			RideInfoBox(departure, "departure", Modifier.weight(1f))
			RideInfoBox(seatsAvailable, "seats available", Modifier.weight(1f))
		}
	}
}

@Composable
private fun RideInfoBox(value: String, label: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.background(Colours.LightModeSecondary, RoundedCornerShape(4.dp)).padding(vertical = 7.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(value, color = Colours.LightModeText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
		Text(label, color = Colours.LightModeText, fontSize = 12.sp)
	}
}

@Composable
fun DiscussingRideBanner(startCity: String, endCity: String, date: String, time: String, price: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxWidth().background(Colours.DarkModePrimary, RoundedCornerShape(10.dp)).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
		Text("DISCUSSING THIS RIDE", color = Colours.DarkModeText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
		Text("$startCity  ->  $endCity", color = Colours.DarkModeText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
		Text("$date   $time   $price per seat", color = Colours.DarkModeText, fontSize = 12.sp)
	}
}
