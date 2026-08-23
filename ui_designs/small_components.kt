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
private const val carBodyAsset = "https://www.figma.com/api/mcp/asset/3cadbe92-c3b5-4af9-b981-8989ebb52a45.svg"
private const val carBackWheelAsset = "https://www.figma.com/api/mcp/asset/e3a8667f-5eae-4cb7-b968-82fbbd5868e9.svg"
private const val carFrontWheelAsset = "https://www.figma.com/api/mcp/asset/23493609-b260-4ca6-a63a-5de3df40df53.svg"
private const val profileBodyAsset = "https://www.figma.com/api/mcp/asset/4f7672c0-25f1-4d52-b60b-8f0fa33559b2.svg"
private const val profileHeadAsset = "https://www.figma.com/api/mcp/asset/8ab73d6d-6812-450d-a4c2-973d334dce6a.svg"

@Composable
private fun carIcon() {
    Box(Modifier.size(width = 42.dp, height = 21.dp)) {
		AsyncImage(carBackWheelAsset, null, Modifier.size(7.dp).align(Alignment.BottomEnd).padding(end = 6.dp))
		AsyncImage(carFrontWheelAsset, null, Modifier.size(7.dp).align(Alignment.BottomStart).padding(start = 7.dp))
		AsyncImage(carBodyAsset, label, Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
	}
}

@Composable
private fun profileIcon() {
    Box(Modifier.size(24.dp)) {
		AsyncImage(profileBodyAsset, null, Modifier.size(20.dp).align(Alignment.BottomCenter))
		AsyncImage(profileHeadAsset, null, Modifier.size(9.dp).align(Alignment.TopCenter))
	}
}