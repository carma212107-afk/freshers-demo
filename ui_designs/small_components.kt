import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


// icons


// BottomNavigationBar icons
@Composable
fun homeIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 23.dp, height = 24.dp)) {
		val homePath = androidx.compose.ui.graphics.Path().apply {
			moveTo(1f, 10f)
			lineTo(11.5f, 1f)
			lineTo(22f, 10f)
			lineTo(20f, 10f)
			lineTo(20f, 23f)
			lineTo(3f, 23f)
			lineTo(3f, 10f)
			close()
		}
		drawPath(homePath, Colours.LightModePrimary, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f, join = StrokeJoin.Round))
		drawRect(Colours.LightModePrimary, topLeft = androidx.compose.ui.geometry.Offset(8f, 14f), size = androidx.compose.ui.geometry.Size(7f, 9f))
	}
}
@Composable
fun pinIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 15.734.dp, height = 19.74.dp)) {
		val pinPath = androidx.compose.ui.graphics.Path().apply {
			moveTo(7.87f, 19f)
			cubicTo(6.8f, 17f, 1f, 12.2f, 1f, 7.4f)
			cubicTo(1f, 3.8f, 4.1f, 1f, 7.87f, 1f)
			cubicTo(11.6f, 1f, 14.73f, 3.8f, 14.73f, 7.4f)
			cubicTo(14.73f, 12.2f, 8.9f, 17f, 7.87f, 19f)
		}
		drawPath(pinPath, Colours.LightModePrimary, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f, join = StrokeJoin.Round))
		drawCircle(Colours.LightModePrimary, radius = 3f, center = androidx.compose.ui.geometry.Offset(7.87f, 7.3f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f))
	}
}
@Composable
fun carIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 42.dp, height = 20.699.dp)) {
		drawRoundRect(Colours.LightModePrimary, topLeft = androidx.compose.ui.geometry.Offset(1f, 5f), size = androidx.compose.ui.geometry.Size(40f, 11f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f))
		drawCircle(Colours.LightModePrimary, radius = 1.4f, center = androidx.compose.ui.geometry.Offset(9f, 16.5f))
		drawCircle(Colours.LightModePrimary, radius = 1.4f, center = androidx.compose.ui.geometry.Offset(33f, 16.5f))
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(12f, 5f), androidx.compose.ui.geometry.Offset(17f, 1.5f), 2f, StrokeCap.Round)
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(17f, 1.5f), androidx.compose.ui.geometry.Offset(30f, 1.5f), 2f, StrokeCap.Round)
	}
}
@Composable
fun profileIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 20.dp, height = 22.533.dp)) {
		drawCircle(Colours.LightModePrimary, radius = 4.75f, center = androidx.compose.ui.geometry.Offset(10f, 5f))
		drawRoundRect(Colours.LightModePrimary, topLeft = androidx.compose.ui.geometry.Offset(1f, 13f), size = androidx.compose.ui.geometry.Size(18f, 9.5f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f))
	}
}

@Composable
fun BagsIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 21.dp, height = 26.dp)) {
		drawRoundRect(Colours.LightModePrimary, topLeft = androidx.compose.ui.geometry.Offset(1f, 5f), size = androidx.compose.ui.geometry.Size(12f, 20f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f))
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(5f, 5f), androidx.compose.ui.geometry.Offset(5f, 1f), 2f, StrokeCap.Round)
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(5f, 1f), androidx.compose.ui.geometry.Offset(9f, 1f), 2f, StrokeCap.Round)
		drawRoundRect(Colours.LightModePrimary, topLeft = androidx.compose.ui.geometry.Offset(8f, 18f), size = androidx.compose.ui.geometry.Size(12f, 7f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(1f, 1f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f))
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(11f, 18f), androidx.compose.ui.geometry.Offset(17f, 15f), 2f, StrokeCap.Round)
	}
}

@Composable
fun MessageIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 23.dp, height = 20.dp)) {
		drawRoundRect(Colours.LightModePrimary, topLeft = androidx.compose.ui.geometry.Offset(1f, 1f), size = androidx.compose.ui.geometry.Size(21f, 17f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f))
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(5f, 18f), androidx.compose.ui.geometry.Offset(4f, 14f), 2f, StrokeCap.Round)
	}
}

@Composable
fun SearchIcon(modifier: Modifier = Modifier) {
	Canvas(modifier.size(width = 15.734.dp, height = 19.74.dp)) {
		drawCircle(Colours.LightModePrimary, radius = 6.5f, center = androidx.compose.ui.geometry.Offset(6.5f, 6.5f), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f))
		drawLine(Colours.LightModePrimary, androidx.compose.ui.geometry.Offset(11f, 11f), androidx.compose.ui.geometry.Offset(15f, 15f), 2f, StrokeCap.Round)
	}
}

@Composable
fun CalendarIcon(modifier: Modifier = Modifier) {
	Icon(Icons.Outlined.DateRange, contentDescription = "Calendar", modifier = modifier.size(20.dp), tint = Colours.LightModePrimary)
}
@Composable
fun ClockIcon(modifier: Modifier = Modifier) {
	Icon(Icons.Outlined.AccessTime, contentDescription = "Clock", modifier = modifier.size(20.dp), tint = Colours.LightModePrimary)
}


// Seat icons and no_of_free_seats indicator
@Composable
fun IndividualSeatIcon(available: Boolean = false, modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.size(10.dp).border(1.dp, Colours.LightModePrimary, RoundedCornerShape(3.dp))
			.background(if (available) Colours.LightModeBackground1 else Colours.LightModePrimary, RoundedCornerShape(3.dp)),
	)
}
@Composable
fun NoOfFreeSeatsIndicator(modifier: Modifier = Modifier, noOfFreeSeats: Int = 3, noOfSeats: Int = 4) {
	Row(modifier = modifier.padding(horizontal = 2.dp), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
		for (i in 1..noOfSeats) {
            IndividualSeatIcon(available = i <= noOfFreeSeats)
        }
	}
}



@Composable
fun BackButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.size(40.dp).background(Colours.DarkModePrimary, CircleShape)
			.border(1.dp, Colours.DarkModeBorder, CircleShape).clickable(onClick = onClick),
		contentAlignment = Alignment.Center,
	) {
		Canvas(Modifier.size(22.dp)) {
			drawLine(Colours.DarkModeSecondary, androidx.compose.ui.geometry.Offset(15f, 3f), androidx.compose.ui.geometry.Offset(7f, 11f), 2.5f, StrokeCap.Round)
			drawLine(Colours.DarkModeSecondary, androidx.compose.ui.geometry.Offset(7f, 11f), androidx.compose.ui.geometry.Offset(15f, 19f), 2.5f, StrokeCap.Round)
		}
	}
}