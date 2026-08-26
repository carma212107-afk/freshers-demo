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


@Composable
fun CoverPage(
  onGetStarted: () -> Unit = {},
  onLogin: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  
    StatusBar(theme = "dark")
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
          fontSize = Variables.textIntroTitle.size,
          fontWeight = Variables.textIntroTitle.weight,
        )
        Text(
          text = "Student intercity ridesharing",
          modifier = Modifier
            .padding(top = 4.dp)
            // .containerColor = Variables.DarkModeBackground2
            .border(
              BorderStroke(1.dp, Variables.DarkModeBorder),
              RoundedCornerShape(20.dp),
            )
            .padding(horizontal = 25.dp, vertical = 6.dp),
          color = Variables.DarkModeText,
          fontSize = Variables.textText1.size,
          fontWeight = Variables.textText1.weight,
        )
      }

      StageIndicator(currentStage = 1, noStages = 5, primaryColour = Variables.Accent)

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
          fontSize = Variables.textHeading1.size,
          fontWeight = Variables.textHeading1.weight,
          lineHeight = 52.5.sp,
          textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
          text = "Share rides with verified students.\nSave money.\nCut emissions.",
          color = Variables.DarkModeText,
          fontSize = Variables.textText1.size,
          fontWeight = Variables.textText1.weight,
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
          Text("Get started", fontWeight = Variables.textButton1.weight, fontSize = Variables.textButton1.size)
        }
        Button(
          onClick = onLogin,
          modifier = Modifier
            .fillMaxWidth()
            .height(43.dp),
          shape = RoundedCornerShape(20.dp),
          border = BorderStroke(1.dp, Variables.DarkModeBorder),
          colors = ButtonDefaults.buttonColors(
            containerColor = Variables.DarkModeBackground1,
            contentColor = Variables.DarkModeText,
          ),
        ) {
          Text("I already have an account", fontWeight = Variables.textButton2.weight, fontSize = Variables.textButton2.size)
        }
      }
    }
  }
}


object Variables {
  val DarkModeBackground1: Color = Colours.DarkModeBackground1
  val DarkModeBackground2: Color = Colours.DarkModeBackground2
  val DarkModeBorder: Color = Colours.DarkModeBorder
  val DarkModeText: Color = Colours.DarkModeText
  val LightModeBackground: Color = Colours.LightModeBackground
  val LightModeText: Color = Colours.LightModeText
  val Accent: Color = Colours.Accent

  val xUI = Dimensions.currentUIx
  val xUI = Dimensions.currentUIy

  val textIntroTitle = TextFormatting.IntroTitle
  val textText1 = TextFormatting.Text1
  val textHeading1 = TextFormatting.Heading1
  val textButton1 = TextFormatting.Button1
  val textButton2 = TextFormatting.Button2
}

