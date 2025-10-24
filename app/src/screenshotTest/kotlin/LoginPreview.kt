import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.android.tools.screenshot.PreviewTest
import com.example.composescreenshotofficial.fakeLogin

@PreviewTest
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLogin = { email, password, remember ->
            fakeLogin(email, password, remember)
        },
        onForgotPassword = {
            // Handle "Forgot password" navigation
        },
        onSignUp = {
            // Handle "Sign up" navigation
        },
        navController = rememberNavController()
    )
}
