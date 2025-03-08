import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Checkbox from "@mui/material/Checkbox";
import PropTypes from "prop-types";
import * as React from "react";
import { useEffect } from "react";
const headCells = [
    {
        id: 'name',
        align: 'left',
        disablePadding: true,
        label: 'ФИО Сотрудника',
    },
    {
        id: 'description',
        align: 'center',
        disablePadding: false,
        label: 'Внёс изменения',
    },
    {
        id: 'datetime',
        align: 'right',
        disablePadding: false,
        label: 'Дата и время',
    },
];


export default function LogTableHead(props) {
    const { onSelectAllClick, numSelected, rowCount } =
        props;

    useEffect(() => {
        console.log("Количество numSelected:", numSelected);
    }, [numSelected]);

    useEffect(() => {
        console.log("Количество rowCount:", rowCount);
    }, [rowCount]);

    return (
        <TableHead>
            <TableRow>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        indeterminate={numSelected > 0 && numSelected < rowCount}
                        checked={rowCount > 0 && numSelected === rowCount}
                        onChange={onSelectAllClick}
                        inputProps={{
                            'aria-label': 'select all logs',
                        }}
                    />
                </TableCell>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align={headCell.id === 'name' ? 'left' : headCell.align}
                        padding={headCell.disablePadding ? 'none' : 'normal'}
                    >
                        {headCell.label}
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

LogTableHead.propTypes = {
    numSelected: PropTypes.number.isRequired,
    onSelectAllClick: PropTypes.func.isRequired,
    rowCount: PropTypes.number.isRequired,
};
