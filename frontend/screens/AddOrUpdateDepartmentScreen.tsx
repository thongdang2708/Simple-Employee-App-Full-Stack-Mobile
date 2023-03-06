
import React, { useContext, useEffect, useLayoutEffect, useState } from 'react';
import {View, Text, StyleSheet} from "react-native";
import InputForUpdating from '../components/InputForUpdating';
import colors from '../constants/colors';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import AuthContext from '../store/context/AuthContext';
import { RootParamList } from '../App';
import { dispatchStore, RootState } from '../store/redux/store';
import { getSingleDepartment } from '../store/redux/actions/DepartmentActions';
import { useSelector } from 'react-redux';
import { Department } from '../store/redux/reducers/DepartmentReducers';
import ButtonForForm from '../ui/ButtonForForm';
import ButtonForMainScreen from '../ui/ButtonForMainScreen';
import { addDepartment } from '../store/redux/actions/DepartmentActions';
import { updateDepartment } from '../store/redux/actions/DepartmentActions';

function AddOrUpdateDepartmentScreen({navigation, route} : any) {

    //Get params
    let departmentId = route.params?.id;
  
    //Check params
    let addOrUpdate = !!departmentId;
   
    //Set navigation
    let navigation2 = useNavigation<StackNavigationProp<RootParamList>>();

    //Get auth context
    let {isAuthenticated, token} = useContext(AuthContext);

    //Get single department from redux
    let {departments} = useSelector((state : RootState) => state.department);

    //Filter single department
    let totalArrayOfDepartments = departments as Department[];
    let singleDepartment = totalArrayOfDepartments.filter((department : Department) => department.id === departmentId)[0]; 

  

    //Get managerId from user from redux
    let {managerId} = useSelector((state : RootState) => state.user);

   

    //Set state for form input
    let [input, setInput] = useState({
        name: !addOrUpdate ? "" : singleDepartment.name
    });

    

    //Set state for checking for form input
    let [isValid, setIsValid] = useState<boolean>(true);

    //Check authorization
    useEffect(() => {
        if (!isAuthenticated || token == null) {
            navigation2.navigate("LogInScreen")
        }
    },[isAuthenticated, token]);

    //Set layout effect
    useLayoutEffect(() => {

        if (!addOrUpdate) {
            navigation.setOptions({
                headerTitle: "Add Department Screen"
            })
        } else {
            navigation.setOptions({
                headerTitle: "Update Department Screen"
            });
        }
    },[navigation, addOrUpdate]);

    

    //Handle change for inputs
    const handleChange = (key: string, value : string) => {
        
        setInput((values) => {
            return {
                ...values,
                [key]: value
            }
        });
    };

    //Handle submit

    const handleSubmit = () => {

        let thisDate = new Date();
        let year = thisDate.getFullYear();
        let month = thisDate.getMonth() + 1;
        let date = thisDate.getDate();

        let castedMonth = month < 10 ? "0" + month : month;
        let castedDate = date < 10 ? "0" + date : date;
        let fullDate = year + "-" + castedMonth + "-" + castedDate;

        console.log(fullDate);
        
        let nameIsValid = input.name.length >= 2;

        if (!nameIsValid) {
            setIsValid(false);

            setTimeout(() => {
                setIsValid(true);
            }, 4000);
            return;
        }

    
        if (!addOrUpdate) {
            let newInput = {
                name: input.name,
                createdAt: fullDate
            }

            dispatchStore(addDepartment(token as string, newInput, managerId) as any);
            setInput({
                name: ""
            });
        } else {
            let newInput = {
                name: input.name,
                createdAt: singleDepartment.createdAt
            };

            dispatchStore(updateDepartment(token as string, departmentId, newInput) as any);
        }

        navigation2.goBack();
        
    }


  return (
    <View style={styles.screen}>
        <View>
            <Text style={styles.title}> {!addOrUpdate ? "Add Department Form" : "Update Department Form"} </Text>
        </View>

        <View style={styles.form}>
            <InputForUpdating label='Name of department:' textInputConfig={{
                placeholder: "Enter a deparment",
                autoCorrect: false,
                autoCapitalize: "none",
                value: input.name,
                onChangeText: (text : string) => handleChange("name", text)
            }} 
            inputValid={isValid == false}
            textValid={"Name of department must be at least 2 characters"}
            />

            <ButtonForMainScreen onPress={handleSubmit}>
                {!addOrUpdate ? "Add Department" : "Update Department"}
            </ButtonForMainScreen>
        </View>
        
    </View>
  )
};

const styles = StyleSheet.create({
    screen: {
        flex: 1,
        padding: 10,
        backgroundColor: colors.skinColor
    },
    title: {
        fontSize: 24,
        fontWeight: "bold",
        textAlign: "center",
        marginVertical: 15
    },
    form: {
        marginVertical: 10
    }
});

export default AddOrUpdateDepartmentScreen