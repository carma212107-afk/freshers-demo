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

private const val carmaLogoAsset =
  "https://www.figma.com/api/mcp/asset/e1afa15d-63da-47ec-94a8-58773cf9ff06.png"
private const val timeAsset = 
  "https://www.figma.com/api/mcp/asset/a3121e18-4222-405c-9eca-d748c5f06502.svg"
private const val rightSideAsset =
  "https://www.figma.com/api/mcp/asset/3042e646-380b-478e-a3ec-48dbd73ad777.svg"

@Composable
fun CoverPage(
  onGetStarted: () -> Unit = {},
  onLogin: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  Box( // Status bar & Home indicator
    modifier = modifier
      .fillMaxSize()
      .background(Variables.DarkModeBackground),
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


    // Main content

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 75.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) { // App name and tagline
        Text(
          text = "Carma",
          color = Variables.DarkModeText,
          fontSize = 50.sp,
          fontWeight = FontWeight.Black,
        )
        Text(
          text = "Student intercity ridesharing",
          modifier = Modifier
            .padding(top = 4.dp)
            // .containerColor = Variables.DarkModePrimary
            .border(
              BorderStroke(1.dp, Variables.DarkModeBorder),
              RoundedCornerShape(20.dp),
            )
            .padding(horizontal = 25.dp, vertical = 6.dp),
          color = Variables.DarkModeText,
          fontSize = 16.sp,
          fontWeight = FontWeight.Medium,
        )
      }

      StageIndicator(currentStage = 1, noStages = 5)

      AsyncImage( // Carma logo
        model = carmaLogoAsset,
        contentDescription = "Carma ridesharing logo",
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.Crop,
      )

      Column( // Description text
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = "The smarter way\nto travel between\nuni cities.",
          color = Variables.DarkModeText,
          fontSize = 35.sp,
          fontWeight = FontWeight.Black,
          lineHeight = 52.5.sp,
          textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
          text = "Share rides with verified students.\nSave money.\nCut emissions.",
          color = Variables.DarkModeText,
          fontSize = 16.sp,
          lineHeight = 22.4.sp,
          textAlign = TextAlign.Center,
        )
      }

      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
      ) {
        Button(
          onClick = onGetStarted,
          modifier = Modifier
            .fillMaxWidth()
            .height(43.dp),
          shape = RoundedCornerShape(20.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Variables.LightModeBackground,
            contentColor = Variables.LightModeText,
          ),
        ) {
          Text("Get started", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Button(
          onClick = onLogin,
          modifier = Modifier
            .fillMaxWidth()
            .height(43.dp),
          shape = RoundedCornerShape(20.dp),
          border = BorderStroke(1.dp, Variables.DarkModeBorder),
          colors = ButtonDefaults.buttonColors(
            containerColor = Variables.DarkModeBackground,
            contentColor = Variables.DarkModeText,
          ),
        ) {
          Text("I already have an account", fontSize = 16.sp)
        }
      }
    }
  }
}


object Variables {
  val DarkModeBackground: Color = Colours.DarkModeBackground
  val DarkModePrimary: Color = Colours.DarkModePrimary
  val DarkModeBorder: Color = Colours.DarkModeBorder
  val DarkModeText: Color = Colours.DarkModeText
  val LightModeBackground: Color = Colours.LightModeBackground
  val LightModeText: Color = Colours.LightModeText
  val Accent: Color = Colours.Accent
  val xUI = Dimensions.currentUIx
  val xUI = Dimensions.currentUIy
}

