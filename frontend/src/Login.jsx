import {
    Container, Box, TextField, Button, Typography, Link,
    IconButton, InputAdornment
} from '@mui/material';
import { useState } from 'react';
import HomeIcon from '@mui/icons-material/Home';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { Link as RouterLink } from 'react-router-dom';
import ForgotPasswordDialog from './ForgotPasswordDialog';

export default function Login() {
    const [email, setEmail] = useState("");
    const [emailError, setEmailError] = useState(false);
    const [emailErrorText, setEmailErrorText] = useState("");

    const [password, setPassword] = useState("");
    const [passwordError, setPasswordError] = useState(false);
    const [passwordErrorText, setPasswordErrorText] = useState("");


    const [showPassword, setShowPassword] = useState(false);


    const [openDialog, setOpenDialog] = useState(false);
    const handleForgotPasswordClicked = () => {
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
    };

    const handleSubmit = (e) => {
        console.log("handle submit")
        e.preventDefault();

        setEmailError(false);
        setPasswordError(false);
        setEmailErrorText("");
        setPasswordErrorText("");

        let isValid = true;

        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(email)) {
            setEmailError(true);
            setEmailErrorText("Введите корректный email");
            isValid = false;
        }
        else {
            console.log("email валиден")
        }

        if (password === "") {
            setPasswordError(true);
            setPasswordErrorText("Введите пароль");
        }

        if (isValid) {
            console.log("Форма отправлена");
        } else {
            console.log("Ошибка валидации, форма не отправлена");
        }
    };

    return (
        <Container
            maxWidth="xs"
            sx={{
                height: '100vh', // Задаем полную высоту экрана
                display: 'flex',
                alignItems: 'center', // Центрируем по вертикали
                justifyContent: 'center', // Центрируем по горизонтали
            }}
        >
            <Box
                sx={{
                    position: 'absolute',
                    top: 16,
                    right: 16,
                    display: 'flex', // Горизонтальное выравнивание
                    alignItems: 'center', // Центрируем элементы по вертикали
                }}
            >
                <Link color="inherit" component={RouterLink} to="/" sx={{ textDecoration: 'none', color: 'primary.main' }}>
                    Вернуться на главную
                </Link>
                <HomeIcon sx={{ ml: 0.7, color: 'primary.main' }} />
            </Box>

            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    p: 3,
                    boxShadow: 3,
                    borderRadius: 2,
                }}
            >
                <Typography variant="h5" gutterBottom>
                    Логин
                </Typography>

                <Box
                    component="form"
                    noValidate
                    spellCheck="off"
                    autoCapitalize="off"
                    onSubmit={(e) => handleSubmit(e)}
                >
                    <TextField
                        autoComplete="new-password"
                        autoCorrect="off"
                        spellCheck="false"
                        label="Email"
                        type="email"
                        fullWidth
                        variant="outlined"
                        margin="normal"
                        value={email}
                        error={emailError}
                        helperText={emailErrorText}
                        required
                        onChange={(e) => setEmail(e.target.value)}
                        onFocus={(e) => {
                            console.log("email focused")
                            e.preventDefault()
                        }}
                    />
                    <TextField
                        autoComplete="new-password"
                        label="Пароль"
                        type={showPassword ? 'text' : 'password'}
                        fullWidth
                        variant="outlined"
                        margin="normal"
                        value={password}
                        error={passwordError}
                        helperText={passwordErrorText}
                        required
                        onChange={(e) => {
                            const newValue = e.target.value.replace(/\s+/g, "");
                            setPassword(newValue);
                        }}
                        onFocus={(e) => {
                            console.log("password focused")
                            e.preventDefault()
                        }}

                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton
                                        onClick={() => setShowPassword(!showPassword)}
                                        edge="end"
                                    >
                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                    <Box
                        sx={{
                            display: 'flex',
                            justifyContent: 'flex-end',
                            width: '100%',
                        }}
                    >
                        <Link href="#" variant="body2" sx={{ textDecoration: 'none' }}
                            onClick={(e) => { handleForgotPasswordClicked(e) }}
                        >
                            Забыли пароль?
                        </Link>
                        <ForgotPasswordDialog 
                            open={openDialog}
                            handleClose={handleCloseDialog}
                        />


                    </Box>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ mt: 2 }}
                    >
                        Войти
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}