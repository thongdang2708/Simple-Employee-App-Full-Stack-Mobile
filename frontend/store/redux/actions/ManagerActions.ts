
import axios from "axios";
import { Dispatch } from "redux";
import { ManagerAction } from "../reducers/ManagerReducers";

import { WEB_URL } from "@env";

// Get information of specific user

export const getManagerUserInformation = (id : number, token: string) =>  async (dispatch: Dispatch<ManagerAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }

    let response = await axios.get(WEB_URL + "/managers/" + id, config);

    let data = response.data;

    let newObjectManager = {
        managerForId: data.id,
        address: data.address === null ? "" : data.address,
        city: data.city === null ? "" : data.city,
        role: data.role
    }

    dispatch({
        type: "FETCH_MANAGER_SUCCESSFULLY",
        payload: newObjectManager
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();
        
        dispatch({
            type: "FETCH_MANAGER_FAIL",
            payload: message
        })
    }
};

interface InputProps {
    address: string,
    city: string
}

//Update manager information

export const updateManagerInformation = (token : string, id: number, input : InputProps) => async (dispatch: Dispatch<ManagerAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.put(WEB_URL + "/managers/" + id, input, config);

    let data = response.data;

    console.log(data);

    dispatch({
        type: "UPDATE_MANAGER_INFO_SUCCESS",
        payload: data
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();
        
        dispatch({
            type: "UPDATE_MANAGER_INFO_FAIL",
            payload: message
        });
        
    }
};