import {
    Container, Box, Button, Typography, Link, AppBar, Toolbar
} from '@mui/material';
import { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';

import EnhancedTable from './LogTable'

export default function Home() {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    return (
        <Box sx={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
            <AppBar position="fixed" sx={{ color: 'primary.main' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Учёт изменений состояния сервера
                    </Typography>

                    <Link color="inherit" component={RouterLink} to="/login">
                        <Button color="inherit">{isLoggedIn ? "Выйти" : "Войти"}</Button>
                    </Link>
                </Toolbar>
            </AppBar>
            <Container
                maxWidth="md"
                sx={{
                    flexGrow: 1,
                    marginTop: '64px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                {isLoggedIn ? (
                    <EnhancedTable>
                    </EnhancedTable>
                ) : (
                    <Typography variant="h5">Войдите, чтобы оставить запись</Typography>
                )}
            </Container>
        </Box>
    );
}