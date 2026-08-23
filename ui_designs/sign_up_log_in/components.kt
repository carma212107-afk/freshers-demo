import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private const val timeAsset = 
  "https://www.figma.com/api/mcp/asset/a3121e18-4222-405c-9eca-d748c5f06502.svg"
private const val rightSideAsset =
  "https://www.figma.com/api/mcp/asset/3042e646-380b-478e-a3ec-48dbd73ad777.svg"


@Composable
private fun StatusBar(modifier: Modifier = Modifier, theme: string = "light") {
    if (theme == "light") { colour = Color(0xFF0A5C2E) }
    else if (theme == "dark") { colour = Color(0xFFFFFFFF) }
    
    Box( // Status bar & Home indicator
    modifier = modifier
      .fillMaxSize()
      .background(colour),
  ) {
    Row(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 20.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AsyncImage(
        model = timeAsset,
        contentDescription = "Current time",
        modifier = Modifier.size(width = 54.dp, height = 21.dp),
      )
      AsyncImage(
        model = rightSideAsset,
        contentDescription = "Signal, Wi-Fi, and battery status",
        modifier = Modifier.size(width = 67.dp, height = 12.dp),
      )
    }
}

@Composable
private fun StageIndicator(modifier: Modifier = Modifier, currentStage: Int = 1, noStages: Int = 5, primaryColour: Color = Variables.LightModePrimary, secondaryColour: Color = Variables.LightModeSecondary) {
  Column(

    // determine position of current stage indicator based on int parameter
    noStages = noStages
    currentStage = currentStage,
    noStagesBefore = currentStage - 1,
    noStagesAfter = noStages - currentStage,

    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    androidx.compose.foundation.layout.Row(
      horizontalArrangement = Arrangement.spacedBy(5.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      repeat(noStagesBefore) {
        AsyncImage(
          model = IndividualStageIndicator(isCurrentStage = false, color = secondaryColour),
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      }
      AsyncImage(
          model = IndividualStageIndicator(isCurrentStage = true, color = primaryColour),
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      repeat(noStagesAfter) {
        AsyncImage(
          model = IndividualStageIndicator(isCurrentStage = false, color = secondaryColour),
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      }
    }
  }
}

@Composable
private fun IndividualStageIndicator(modifier: Modifier = Modifier, isCurrentStage: Boolean = false, colour: Color = Variables.LightModeSecondary) {
  if (isCurrentStage) {
    Box(
      modifier = Modifier
        .size(width = 25.dp, height = 10.dp)
        .background(color = primaryColour, shape = RoundedCornerShape(size = 20.dp)),
    )
  } else {
    Ellipse(
      modifer = Modifier
        .padding(1.dp)
        .size(width=10.dp, height=10.dp)
        .background(color = secondaryColour)
    )
  }
}

Object Variables {
  val LightModePrimary = Colours.LightModePrimary
  val LightModeSecondary = Colours.LightModeSecondary
}