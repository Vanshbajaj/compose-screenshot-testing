import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun LoginScreen(
    onLogin: suspend (email: String, password: String, remember: Boolean) -> Boolean,
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val elevationAnim by animateFloatAsState(targetValue = if (isLoading) 0.5f else 1f)

    fun validate(): Boolean {
        var ok = true
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Enter a valid email"
            ok = false
        } else {
            emailError = null
        }

        if (password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            ok = false
        } else {
            passwordError = null
        }

        return ok
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Spacer(Modifier.height(48.dp))

                LogoHeader(elevation = elevationAnim.dp)

                Spacer(Modifier.height(20.dp))

                HeaderTexts()

                Spacer(Modifier.height(28.dp))

                LoginCard(
                    email = email,
                    onEmailChange = {
                        email = it
                        if (emailError != null) emailError = null
                    },
                    emailError = emailError,
                    password = password,
                    onPasswordChange = {
                        password = it
                        if (passwordError != null) passwordError = null
                    },
                    passwordError = passwordError,
                    passwordVisible = passwordVisible,
                    onTogglePasswordVisible = { passwordVisible = !passwordVisible },
                    rememberMe = rememberMe,
                    onRememberChange = { rememberMe = it },
                    onForgotPassword = onForgotPassword,
                    isLoading = isLoading,
                    onSubmit = {
                        focusManager.clearFocus()
                        if (!validate()) return@LoginCard
                        if (!isLoading) {
                            isLoading = true
                            scope.launch {
                                val success = onLogin(email.trim(), password, rememberMe)
                                isLoading = false
                                if (!success) {
                                    snackbarHostState.showSnackbar("Invalid credentials. Please try again.")
                                }
                            }
                        }
                    }
                )

                Spacer(Modifier.height(18.dp))

                SignUpRow(onSignUp)
            }
        }
    }
}

@Composable
private fun LogoHeader(elevation: Dp) {
    Surface(
        tonalElevation = 4.dp,
        shape = CircleShape,
        shadowElevation = elevation,
        modifier = Modifier.size(88.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "App logo",
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun HeaderTexts() {
    Text(
        text = "Welcome back",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
    )
    Text(
        text = "Sign in to continue",
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.7f
            )
        )
    )
}

@Composable
private fun LoginCard(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    passwordVisible: Boolean,
    onTogglePasswordVisible: () -> Unit,
    rememberMe: Boolean,
    onRememberChange: (Boolean) -> Unit,
    onForgotPassword: () -> Unit,
    isLoading: Boolean,
    onSubmit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            EmailField(value = email, onValueChange = onEmailChange, error = emailError)
            Spacer(Modifier.height(12.dp))
            PasswordField(
                value = password,
                onValueChange = onPasswordChange,
                visible = passwordVisible,
                onToggleVisibility = onTogglePasswordVisible,
                error = passwordError
            )
            Spacer(Modifier.height(12.dp))
            RememberAndForgot(
                rememberMe = rememberMe,
                onRememberChange = onRememberChange,
                onForgotPassword = onForgotPassword
            )
            Spacer(Modifier.height(8.dp))
            LoginButton(isLoading = isLoading, onClick = onSubmit)
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    "  or  ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
            // Additional social auth or other actions could go here
        }
    }
}

@Composable
private fun EmailField(value: String, onValueChange: (String) -> Unit, error: String?) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        placeholder = { Text("name@example.com") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email icon") },
        singleLine = true,
        isError = error != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .semantics { /* optional */ }
    )
    error?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
        )
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onToggleVisibility: () -> Unit,
    error: String?
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        placeholder = { Text("Enter password") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password icon") },
        trailingIcon = {
            val toggleDesc = if (visible) "Hide password" else "Show password"
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (visible) Icons.Default.AccountBox else Icons.Default.AccountCircle,
                    contentDescription = toggleDesc
                )
            }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        isError = error != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { /* hand off to caller */ }),
        modifier = Modifier
            .fillMaxWidth()
            .semantics { /* optional */ }
    )
    error?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
        )
    }
}

@Composable
private fun RememberAndForgot(
    rememberMe: Boolean,
    onRememberChange: (Boolean) -> Unit,
    onForgotPassword: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = rememberMe,
            onCheckedChange = onRememberChange
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Remember me",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable { onRememberChange(!rememberMe) }
        )
        Spacer(Modifier.weight(1f))
        TextButton(onClick = onForgotPassword) {
            Text("Forgot?", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun LoginButton(isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.width(12.dp))
            Text("Signing in...")
        } else {
            Text("Sign in")
        }
    }
}

@Composable
private fun SignUpRow(onSignUp: () -> Unit) {
    Row {
        Text("Don't have an account?", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.width(8.dp))
        TextButton(onClick = onSignUp) {
            Text("Sign up")
        }
    }
}
