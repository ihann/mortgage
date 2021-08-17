import {React, useState} from 'react';
import config from '../config'
import './Calculator.css'
import {Button, Container, FormControl, Grid, InputLabel, Select, TextField} from "@material-ui/core";
import MessageBox from "./MessageBox";
import axios from "axios";

function Calculator() {

    const [messageData, setMessageData] = useState({isOpen: false, message: ""});
    const [state, setState] = useState({
        price: "",
        down: "",
        rate: "",
        amortization: "5",
        scheduleType: "MONTHLY",
        payment: "",

    });

    const handleMessageDialog = (isOpen, message) => {
        setMessageData({
            isOpen: isOpen,
            message: message
        })
    }

    function apiCall() {

        axios(config.hostname + '/calculator/mortgage',
            {
                method: 'post',
                headers: {"Content-Type": "application/json"},
                data: JSON.stringify(state)
            }
        ).then(response => {
            const res = response.data;
            setState({...state, payment: res});
        }).catch((error) => {
            if (error && error.response && error.response.data && error.response.data.message) {
                // console.log(error.response);
                handleMessageDialog(true, error.response.data.message);
            } else {
                handleMessageDialog(true, "Request fail");
            }
        });

        // const requestOpt = {
        //     method: 'POST',
        //     headers: {'Content-Type': 'application/json'},
        //     body: JSON.stringify(state)
        // }
        // let url = config.hostname + "/calculator/mortgage"
        // fetch(url, requestOpt)
        //     .then(response => {
        //         console.log(response);
        //         if (response.status == 200) {
        //             response.
        //         } else {
        //             console.log(response.json());
        //         }
        //     })
        //     // .then(data => setState(
        //     //     {
        //     //         ...state,
        //     //         payment: data
        //     //     }
        //     // ))
        //     .catch(error => {
        //         handleMessageDialog(true, error);
        //         console.log(error);
        //     });
    }

    function handleChange(event) {
        if (event.target.validity.valid) {
            setState({
                ...state,
                [event.target.name]: event.target.value
            });
        }
    }

    function handleSubmit(event) {
        if (state.price && state.down && state.rate && state.amortization && state.scheduleType) {
            apiCall();
        } else {
            handleMessageDialog(true, "Invalid form");
        }
    }


    return (
        <div className="App">
            <header className="App-header">
                <form>
                    <Container maxWidth="sm">
                        <Grid container spacing={5} justifyContent="center">
                            <Grid item xs={4}>
                                <TextField
                                    type="text"
                                    variant="outlined"
                                    color="primary"
                                    label="Price"
                                    inputProps={{pattern: "[0-9]{1,9}"}}
                                    value={state.price}
                                    onChange={e => handleChange(e)}
                                    name="price"
                                />
                            </Grid>
                            <Grid item xs={4}>
                                <TextField
                                    variant="outlined"
                                    name="down"
                                    type="text"
                                    inputProps={{pattern: "[0-9]{1,8}"}}
                                    value={state.down}
                                    onChange={e => handleChange(e)}
                                    color="primary"
                                    label="Down Payment"
                                />
                            </Grid>
                            <Grid item xs={4}>
                                <TextField
                                    variant="outlined"
                                    name="rate"
                                    type="text"
                                    inputProps={{pattern: "^((\\d{1,2}(\\.\\d{0,2})?))$"}}
                                    value={state.rate}
                                    onChange={e => handleChange(e)}
                                    color="primary"
                                    label="Rate"
                                />
                            </Grid>
                        </Grid>
                        <Grid container spacing={5} justifyContent="center">
                            <Grid item xs={4}>
                                <FormControl variant="outlined">
                                    <InputLabel>Amortization</InputLabel>
                                    <Select native label="Amortization"
                                            name="amortization"
                                            onChange={e => handleChange(e)}
                                    >
                                        <option value={5}>5 years</option>
                                        <option value={20}>10 years</option>
                                        <option value={15}>15 years</option>
                                        <option value={20}>20 years</option>
                                        <option value={25}>25 years</option>
                                        <option value={30}>30 years</option>
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={4}>
                                <FormControl variant="outlined">
                                    <InputLabel>Schedule</InputLabel>
                                    <Select native label="Amortization"
                                            name="scheduleType"
                                            onChange={e => handleChange(e)}
                                    >
                                        <option value="MONTHLY">Monthly</option>
                                        <option value="BI_WEEKLY">Bi-Weekly</option>
                                        <option value="ACC_BI_WEEKLY">Accelerate Bi-Weekly</option>
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={4}>
                                <TextField
                                    disabled
                                    variant="outlined"
                                    color="primary"
                                    label="payment"
                                    name="payment"
                                    value={state.payment}
                                />
                            </Grid>
                        </Grid>
                    </Container>

                    <br/>

                    <Button onClick={handleSubmit} variant="contained"
                            color="primary">
                        Calculate
                    </Button>
                </form>
            </header>
            <MessageBox handleDialog={handleMessageDialog} isOpen={messageData.isOpen}
                        message={messageData.message}/>
        </div>
    );

}

export default Calculator;