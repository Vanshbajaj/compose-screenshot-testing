import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.android.tools.screenshot.PreviewTest
import com.example.composescreenshotofficial.SampleScreen


@PreviewTest
@Preview
@Composable
fun SampleScreenPreview() {
    SampleScreen(
        navController = rememberNavController()
    )
}
