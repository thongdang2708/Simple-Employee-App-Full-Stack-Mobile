
export interface UserProps {
    emailUser : string | undefined | null,
    username: string,
    id: number,
    managerId: number,
    isSuccess: boolean,
    isError: boolean,
    message: string
}

let initialValues : UserProps = {
    emailUser: "",
    username: "",
    id: 0,
    managerId: 0,
    isSuccess: false,
    isError: false,
    message: ""
}

export interface UserAction {
    type: string,
    payload?: any
}

export const userReducer = (state : UserProps = initialValues, action : UserAction) => {

    switch(action.type) {
        case "SAVE_USER_WITH_EMAIL":
            return {
                ...state,
                emailUser: action.payload.emailUser,
                username: action.payload.username,
                id: action.payload.id,
                managerId: action.payload.managerId,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "REMOVE_USER":
            return {
                ...state,
                emailUser: "",
                username: "",
                id: 0,
                managerId: 0,
                isSuccess: false,
                isError: false,
                message: ""
            }
        case "SAVE_USER_WITH_EMAIL_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "UPDATE_USERNAME_SUCCESS":
            return {
                ...state,
                username: action.payload,
                isError: false,
                message: ""
            }
        case "UPDATE_USERNAME_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        default: 
            return state
    }
}



