import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostRideConfirmationPage(
	modifier: Modifier = Modifier,
	ride: Ride,
	onViewMyListing: () -> Unit = {},
	onBackToHome: () -> Unit = {},
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(Colours.LightMode.Background1),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(horizontal = 30.dp, vertical = 100.dp)
				.padding(bottom = 100.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(35.dp),
		) {
			ConfirmationCheckmark()

			Column(
				modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(5.dp),
			) {
				Text(
					text = "Ride posted!",
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Heading1.size,
					fontWeight = TextFormatting.Heading1.weight,
					textAlign = TextAlign.Center,
				)
				Text(
					text = "Your ride is now live.",
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Text1.size,
					fontWeight = TextFormatting.Text1.weight,
					textAlign = TextAlign.Center,
				)
				Text(
					text = "Students on this route will be notified and can book a seat instantly.",
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Text1.size,
					fontWeight = TextFormatting.Text1.weight,
					textAlign = TextAlign.Center,
				)
			}

			ViewRideSummary(ride = ride)

            ContinueButtons(continueLabel = "View my listing", backLabel = "Back to home", onContinue = onViewMyListing, onBack = onBackToHome)
		}
	}
}