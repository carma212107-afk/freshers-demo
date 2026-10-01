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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntroPage(
	modifier: Modifier = Modifier,
	introStage: Int = 2, // this page template is for stages 2-3 of intro
	onNext: () -> Unit = {},
	onSkipIntro: () -> Unit = {},
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(Colours.LightMode.Background1),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 30.dp, vertical = 100.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceBetween,
		) {
			StageIndicator(currentStage = introStage, noStages = 5)

			Box( // this is the image placeholder, replace with actual image when available
				modifier = Modifier
					.size(123.dp)
					.background(Colours.LightMode.Secondary, RoundedCornerShape(20.dp))
					.border(1.dp, Colours.LightMode.Primary, RoundedCornerShape(20.dp)),
			)

			val title = when (introStage) {
				2 -> "Need a seat?\nWe’ve got you."
				3 -> "Got a car?\nFill those seats."
				else -> ""
			}
			val titleDesc = when (introStage) {
				2 -> "Find students heading\nyour way and split the fuel cost.\nCheaper than any train, faster than any bus."
				3 -> "You're driving home anyway.znLet Carma fill your empty seats\nand cover your fuel costs automatically."
				else -> ""
			}
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(10.dp),
			) {
				Text(text = title, color = Colours.LightMode.Text, fontSize = TextFormatting.Heading1.size, fontWeight = TextFormatting.Heading1.weight)
				Text(text = titleDesc, color = Colours.LightMode.Text, fontSize = TextFormatting.Text1.size, fontWeight = TextFormatting.Text1.weight)
			}

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(10.dp),
			) {
				if (introStage == 2) {
					IntroBenefit("75% cheaper than the train on most routes")
					IntroBenefit("Door-to-door: no stations, no connections")
					IntroBenefit("Save on emissions by reducing the number\nof vehicles on the road")
					IntroBenefit("Verified students only: everyone is\nID checked")
					IntroBenefit("Female-only rides available for extra comfort")
				} else if (introStage == 3) {
					IntroBenefit("Cover your fuel and drive for a quarter of\nthe usual cost with 3 passengers")
					IntroBenefit("Post in seconds: route, date, seats, done")
					IntroBenefit("Build your rating by earning trust with every journey")
					IntroBenefit("You're in control: choose planned or last-\nminute trips")
				}
			}

			IntroContinueButtons(
				continueLabel = "Next",
				miscLabel = "Skip intro",
				onContinue = onNext,
				onMisc = onSkipIntro,
			)
		}
	}
}


@Composable
private fun IntroBenefit(text: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.Top,
	) {
		Canvas(Modifier.padding(top = 8.dp).size(5.dp)) {
			drawCircle(Colours.LightMode.Text, radius = size.minDimension / 2f)
		}
		Text(
			text = text,
			color = Colours.LightMode.Text,
			fontSize = TextFormatting.Text3.size,
			fontWeight = TextFormatting.Text3.weight,
		)
	}
}