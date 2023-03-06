
import React, { useState } from 'react';
import InputForUpdating from '../components/InputForUpdating';
import {View, Text, StyleSheet} from "react-native";
import { useSelector } from 'react-redux';
import colors from '../constants/colors';
import { RootState } from '../store/redux/store';
import { useContext } from 'react';
import { dispatchStore } from '../store/redux/store';
import { updateUsername } from '../store/redux/actions/UserActions';
import { updateManagerInformation } from '../store/redux/actions/ManagerActions';
import AuthContext from '../store/context/AuthContext';
import ButtonForForm from '../ui/ButtonForForm';
import ButtonForMainScreen from '../ui/ButtonForMainScreen';


function UpdateInfoScreen({navigation} : any) {

    //Get state from user from redux
    let {username, managerId, id} = useSelector((state : RootState) => state.user);

    //Get state from manager from redux
    let {manager} = useSelector((state : RootState) => state.manager);

    //Get auth context
    let {token} = useContext(AuthContext);

   
    //Set state for input
    let [inputs, setInputs] = useState({
        username: username,
        address: manager.address,
        city: manager.city
    });

    //Set state for validating username
    let [usernameIsValid, setUsernameIsValid] = useState(true);

    //Handle change

    const handleChange = (key : string, value : string) => {

      setInputs((values) => {
        return {
          ...values,
          [key]: value
        }
      })
    };

    //Handle update
    const handleUpdate = () => {

      let firstInput = {
        username: inputs.username
      };

      let secondInput = {
        address: inputs.address,
        city: inputs.city
      }

      let usernameValid = firstInput.username.length >= 4 && firstInput.username.length <= 20;
      
      if (!usernameValid) {

        setUsernameIsValid(false);

        setTimeout(() => {
          setUsernameIsValid(true);
        }, 3000);

        return;
      }

      dispatchStore(updateUsername(token as string, id, firstInput) as any);
      dispatchStore(updateManagerInformation(token as string, managerId, secondInput) as any);
      navigation.goBack();

    
      
    };
    

  return (
    <View style={styles.screen}>
        <Text style={styles.title}> Update Personal Information </Text>

        <View style={styles.form}>
          <InputForUpdating label='Username' textInputConfig={{
            value: inputs.username,
            onChangeText: (text : string) => handleChange("username", text),
            autoCapitalize: "none",
            autoCorrect: false
          }} inputValid={usernameIsValid === false} textValid="Please check again! Username must be at least 4 characters and less than or 20 characters"
          />

          <InputForUpdating label='Address' textInputConfig={{
            value: inputs.address,
            autoCapitalize: "none",
            autoCorrect: false,
            onChangeText: (text : string) => handleChange("address", text)
          }}/>

          <InputForUpdating label='City' textInputConfig={{
            value: inputs.city,
            autoCapitalize: "none",
            autoCorrect: false,
            onChangeText: (text : string) => handleChange("city", text)
          }}/>

          <ButtonForMainScreen onPress={handleUpdate}>
              Update Information
          </ButtonForMainScreen>
        </View>
    </View>
  )
}

const styles = StyleSheet.create({
    screen: {
        flex: 1,
        backgroundColor: colors.skinColor
    },
    title: {
        marginVertical: 15,
        textAlign: "center",
        fontSize: 24,
        fontWeight: "bold"
    },
    form: {
      marginVertical: 10
    }
});

export default UpdateInfoScreen