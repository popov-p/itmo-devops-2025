import {
    Container, Box, Typography, AppBar, Toolbar
} from '@mui/material';

import EnhancedTable from './LogTable'

export default function Home() {
    return (
        <Box sx={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
            <AppBar position="fixed" sx={{ color: 'primary.main' }}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Учёт изменений состояния серверной
                    </Typography>
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
                <EnhancedTable>
                </EnhancedTable>
            </Container>
        </Box>
    );
}