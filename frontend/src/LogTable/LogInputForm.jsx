import React from 'react';
import { Container, Box, TextField, Typography, Button } from '@mui/material';
import { useForm, Controller } from 'react-hook-form';

export default function FormContainer() {
  const { control, handleSubmit, formState: { errors } } = useForm();

  const onSubmit = (data) => {
    console.log(data);
  };

  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          padding: 2,
          boxShadow: 3,
          borderRadius: 2,
          backgroundColor: 'white',
        }}
      >
        <Typography variant="h6" gutterBottom>
          Форма
        </Typography>

        {/* Форма с текстовыми полями через React Hook Form */}
        <form onSubmit={handleSubmit(onSubmit)}>
          {/* Поле для ФИО */}
          <Controller
            name="fullName"
            control={control}
            defaultValue=""
            rules={{ required: "Это поле обязательно" }}
            render={({ field }) => (
              <TextField
                {...field}
                label="ФИО"
                variant="outlined"
                fullWidth
                size="small"
                error={!!errors.fullName}
                helperText={errors.fullName ? errors.fullName.message : ""}
                sx={{ marginBottom: 2 }}
              />
            )}
          />

          {/* Поле для текста */}
          <Controller
            name="text"
            control={control}
            defaultValue=""
            rules={{ required: "Это поле обязательно" }}
            render={({ field }) => (
              <TextField
                {...field}
                label="Текст"
                variant="outlined"
                fullWidth
                multiline
                rows={4}
                error={!!errors.text}
                helperText={errors.text ? errors.text.message : ""}
                sx={{ marginBottom: 2 }}
              />
            )}
          />

          {/* Кнопка отправки */}
          <Button variant="contained" color="primary" type="submit">
            Отправить
          </Button>
        </form>
      </Box>
    </Container>
  );
}
