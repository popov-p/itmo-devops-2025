import React from 'react';
import { useForm, Controller } from 'react-hook-form';
import { useRows } from "./LogContexts.jsx";
import { TextField, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';


import axios from "axios";

import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const schema = yup.object().shape({
  employeeName: yup
    .string()
    .required("ФИО обязательно")
    .min(3, "Минимум 3 символа")
    .max(50, "Максимум 50 символов"),
  logMessage: yup
    .string()
    .required("Текст обязателен")
    .min(10, "Минимум 10 символов")
    .max(500, "Максимум 500 символов"),
});


export default function LogFormDialog({
  opened,
  idToEdit,
  closeLogFormDialog,
  employeeName,
  logMessage }) {

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  });
  const { setRows } = useRows([]);
  const onSubmit = async (data) => {
    try {
      console.log(data);

      let response;

      if (idToEdit) {
        response = await axios.put(`http://127.0.0.1:8070/api/logentries/${idToEdit}`, data);
        console.log(`Запись с ID ${idToEdit} обновлена:`, response.data);
      } else {
        response = await axios.post('http://127.0.0.1:8070/api/logentries', data);
        console.log('Новая запись создана:', response.data);
      }

      console.log('Ответ от сервера:', response.data);

      await axios.get('http://127.0.0.1:8070/api/logentries')
        .then((response) => {
          setRows(response.data.reverse());
          console.log("Данные успешно загружены !", "response data: ", response.data);
        })
        .catch((error) => {
          setRows([]);
          console.log("Ошибка загрузки данных с бэкенда: ", error)
        });


      closeLogFormDialog();
    } catch (error) {
      console.error('Ошибка при отправке данных:', error);
    }
    closeLogFormDialog();
  };

  return (
    <Dialog open={opened} onClose={closeLogFormDialog}>
      <DialogTitle sx={{ marginBottom: 1 }}>Добавить запись</DialogTitle>
      <DialogContent>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Controller
            name="employeeName"
            control={control}
            defaultValue={employeeName}
            render={({ field }) => (
              <TextField
                {...field}
                label="ФИО"
                variant="outlined"
                fullWidth
                sx={{ marginBottom: 2, marginTop: 2 }}
                size="small"
                error={!!errors.employeeName}
                helperText={errors.employeeName?.message}
              />
            )}
          />
          <Controller
            name="logMessage"
            control={control}
            defaultValue={logMessage}
            render={({ field }) => (
              <TextField
                {...field}
                label="Текст"
                variant="outlined"
                fullWidth
                multiline
                rows={4}
                error={!!errors.logMessage}
                helperText={errors.logMessage?.message}
                sx={{ marginBottom: 2 }}
              />
            )}
          />
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={closeLogFormDialog} color="primary">
          Отмена
        </Button>
        <Button variant="contained" color="primary" onClick={handleSubmit(onSubmit)}>
          Отправить
        </Button>
      </DialogActions>
    </Dialog>
  );
}
