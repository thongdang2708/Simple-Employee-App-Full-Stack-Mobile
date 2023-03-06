
import axios from "axios";

import { Dispatch } from "redux";
import { WEB_URL } from "@env";
import { UserAction } from "../reducers/UserReducers";
import { UserProps } from "../reducers/UserReducers";
import AsyncStorage from "@react-native-async-storage/async-storage";

//Save user information with email


export const saveUserWithEmail = (email : string | undefined | null, token : string | undefined | null) => async (dispatch: Dispatch<UserAction>, getState : any) => {
   
    try {
      
    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.get(WEB_URL + "/auth/getUser?email=" + email, config);

    let data = response.data;

    console.log(data);
    dispatch({
        type: "SAVE_USER_WITH_EMAIL",
        payload: {
            emailUser: data.email,
            username: data.username,
            id: data.id,
            managerId: data.managerId
        }
    })

   
    
    } catch (error : any) {
        
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "SAVE_USER_WITH_EMAIL_FAIL",
            payload: message
        })

    }
}

//Remove user
export const removeUser = () => async (dispatch:Dispatch<UserAction>, getState : any) => {

    dispatch({
        type: "REMOVE_USER"
    })
};

interface UsernameProps {
    username: string
}

//Update username

export const updateUsername = (token : string, id : number, input : UsernameProps) => async (dispatch: Dispatch<UserAction>, getState: any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.put(WEB_URL + "/auth/updateUser/" + id, input, config);

    let data = response.data;

    console.log(data);

    dispatch({
        type: "UPDATE_USERNAME_SUCCESS",
        payload: data.username
    })


    } catch (error : any) {

        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "UPDATE_USERNAME_FAIL",
            payload: message
        })
    }
};




