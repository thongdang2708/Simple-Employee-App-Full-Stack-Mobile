
import React from 'react';
import {View, Text, TextInput, StyleSheet} from "react-native";
import colors from '../constants/colors';
import { TouchableWithoutFeedback } from 'react-native';
import { Keyboard } from 'react-native';

interface InputProps {
    label: string
    textInputConfig: any,
    inputValid?: boolean,
    textValid?: string
}

function InputForUpdating({label, textInputConfig, inputValid, textValid} : InputProps) {
  return (
    <View style={styles.inputContainer}>
        <Text style={styles.label}> {label}: </Text>

        <TouchableWithoutFeedback onPress={Keyboard.dismiss} 
                          accessible={false}>
        <TextInput {...textInputConfig} style={[styles.input, inputValid && styles.errorInput]}/>
       </TouchableWithoutFeedback>

        {inputValid && (
            <Text style={styles.warning}> {textValid} </Text> 
        )}
    </View>
  )
}

const styles = StyleSheet.create({
    inputContainer: {
        marginVertical: 10,
        marginHorizontal: 15
    },
    label: {
        fontWeight: "bold",
        fontSize: 20,
        marginVertical: 15
    },
    input: {
        padding: 10,
        borderWidth: 2,
        borderRadius: 4,
        backgroundColor: colors.offWhite
    },
    warning: {
        color: colors.red,
        fontSize: 14,
        marginVertical: 12,
        fontWeight: "bold"
    },
    errorInput: {
        borderColor: colors.red,
        borderWidth: 3
    }
});

export default InputForUpdating