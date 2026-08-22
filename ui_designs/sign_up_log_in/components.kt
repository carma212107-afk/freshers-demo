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

@Composable
private fun StageIndicator(modifier: Modifier = Modifier, currentStage: Int = 1, noStages: Int = 5) {
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
          model = ellipseAsset,
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      }
      Box(
        modifier = Modifier
          .size(width = 25.dp, height = 10.dp)
          .background(Variables.Accent, RoundedCornerShape(20.dp)),
      )
      repeat(noStagesAfter) {
        AsyncImage(
          model = ellipseAsset,
          contentDescription = null,
          modifier = Modifier.size(10.dp),
        )
      }
    }
  }
}
