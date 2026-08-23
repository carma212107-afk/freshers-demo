import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


// icons

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