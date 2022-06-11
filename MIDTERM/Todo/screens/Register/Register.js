import { Colors } from "../../constants/Colors"
import React from 'react'
import { 
    View, 
    Text, 
    TouchableOpacity, 
    TextInput,
    Platform,
    StyleSheet ,
    StatusBar,
    Alert
} from 'react-native';
import * as Animatable from 'react-native-animatable'
// import LinearGradient from 'react-native-linear-gradient'
import {LinearGradient} from 'expo-linear-gradient'
import FontAwesome from 'react-native-vector-icons/FontAwesome'
import Feather from 'react-native-vector-icons/Feather'
import {useState} from 'react'
import Spinner from 'react-native-loading-spinner-overlay'
import { getFirestore, setDoc, doc  } from 'firebase/firestore/lite'
import { RegisterFirebase } from "../../firebase/firebase";



export const Register = ({navigation}) => {
  const [data, setData] = useState({
    email: '',
    password: '',
    rePass: '',
    check_email: false,
    hidePass: true
  })

  const [showEmail, setShowEmail] = useState(false)
  const [showPass, setShowPass] = useState(false)
  const [showRePass, setShowRePass] = useState(false)
  const db = getFirestore()

  const textInputValue =(value) => {
    if(value.length !== 0) {
      setData({
        ...data,
        email: value,
        check_email: true
      })
    } else {
      setData({
        ...data,
        email: value,
        check_email: false
      })
    }
  }

  const handlePass = (value) => {
    setData({
      ...data,
      password: value,
    })
  }

  const handleRePass = (value) => {
    setData({
      ...data,
      rePass: value,
    })
  }

  const updatehidePass = () => {
    setData({
      ...data,
      hidePass: !data.hidePass
    })
  }
  const [loading, setLoading] = useState(false)
  const handleSubmit = () => {
    if(data.email === '' || data.password === '') {
      if(data.email === '') {
        setShowEmail(true)
      } else {
        setShowEmail(false)
      }
      if(data.password === '') {
        setShowPass(true)
      } else {
        setShowPass(false)
      }
    } else {
      if(data.password !== data.rePass) {
        setShowRePass(true)
      } else {
        setLoading(true)
        RegisterFirebase(data.email, data.password)
          .then(({user}) => {
            setLoading(false)
            setDoc(doc(db, 'users', user.uid), {})
          })
          .catch((err) => {
            alert(err)
            setLoading(false)
          }) 
        setShowEmail(false)
        setShowPass(false)
        setShowRePass(false)
      }
    }
  }

  return (
    <View style={styles.container}>
      {loading &&
       <Spinner
          visible={true}
          textContent={'Loading...'}
          textStyle={{color: Colors.primary}}
        />
      }
      <View style={styles.header}>
        {/* <StatusBar backgroundColor={Colors.primary} barStyle="light-content"/> */}
        <Text style={styles.text_header}>Welcome Todo App!</Text>
      </View>
      <View style={styles.footer} >
        <Text style={styles.text_footer}>Email</Text>
        <View style={styles.action}>
          <FontAwesome 
            name="user-o"
            color={Colors.primary}
            size={20}
          />
          <TextInput
            placeholder="Enter your email..."
            style={styles.textInput}
            autoCapitalize="none"
            onChangeText={(value) => {
              textInputValue(value)
            }}
          />
          {data.check_email ?
            <Animatable.View
              animation="bounceIn"
            >
              <Feather 
                name="check-circle"
                color="green"
                size={20}
              />
            </Animatable.View>   
            : null
          }
        </View>
          {showEmail &&
            <Text
                  style={{
                    marginLeft: 4,
                    color: Colors.red,
                    fontSize: 12,
                    marginTop: 10
                  }}
                >
                  * Email cannot be empty
            </Text>
          }
        <Text style={[styles.text_footer, {marginTop: 30}]}>Password</Text>
        <View style={styles.action}>
          <FontAwesome 
            name="lock"
            color={Colors.primary}
            size={20}
          />
          <TextInput
            placeholder="Enter your password..."
            style={styles.textInput}
            autoCapitalize="none"
            secureTextEntry={data.hidePass ? true : false}
            onChangeText={(value) => handlePass(value)}
          />
          <TouchableOpacity
            onPress={updatehidePass}
          >
            { data.hidePass ? 
              <Feather 
              name="eye-off"
              color="green"
              size={20}
              />
              :
              <Feather 
                name="eye"
                color="green"
                size={20}
              />
            }
          </TouchableOpacity>
        </View>
        {
          showPass &&
        <Text
              style={{
                marginLeft: 4,
                color: Colors.red,
                fontSize: 12,
                marginTop: 10
              }}
            >
              * Password cannot be empty
        </Text>
        }
        <Text style={[styles.text_footer, {marginTop: 30}]}>Re-Password</Text>
        <View style={styles.action}>
          <FontAwesome 
            name="lock"
            color={Colors.primary}
            size={20}
          />
          <TextInput
            placeholder="Enter your re-password..."
            style={styles.textInput}
            autoCapitalize="none"
            secureTextEntry={data.hidePass ? true : false}
            onChangeText={(value) => handleRePass(value)}
          />
          <TouchableOpacity
            onPress={updatehidePass}
          >
            { data.hidePass ? 
              <Feather 
              name="eye-off"
              color="green"
              size={20}
              />
              :
              <Feather 
                name="eye"
                color="green"
                size={20}
              />
            }
          </TouchableOpacity>
        </View>
        {
          showRePass &&
        <Text
              style={{
                marginLeft: 4,
                color: Colors.red,
                fontSize: 12,
                marginTop: 10
              }}
            >
              * Re-Password don't match!
        </Text>
        }
        <View style={styles.button}>
          <TouchableOpacity 
              onPress={handleSubmit}
              style={{width: '100%'}}
            >
              <LinearGradient
                colors={['#0189C5', Colors.primary]}
                style={styles.signIn}  
              >
                <Text style={[styles.textSign, {color:"#ffffff"}]}>Register</Text>
              </LinearGradient>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.signIn, {
                borderColor: Colors.primary,
                borderWidth: 1,
                marginTop: 15
              }]}
              onPress={() => navigation.goBack()}
            >
              <Text style={styles.textSign}>Login</Text>
            </TouchableOpacity>
        </View>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1, 
    backgroundColor: Colors.primary
  },
  header: {
      flex: 2,
      justifyContent: 'flex-end',
      paddingHorizontal: 20,
      paddingBottom: 50
  },
  footer: {
      flex:5,
      backgroundColor: '#fff',
      borderTopLeftRadius: 30,
      borderTopRightRadius: 30,
      paddingHorizontal: 20,
      paddingVertical: 30
  },
  text_header: {
      color: '#fff',
      fontWeight: 'bold',
      fontSize: 30
  },
  text_footer: {
      color: '#05375a',
      fontSize: 18
  },
  action: {
      flexDirection: 'row',
      marginTop: 15,
      borderBottomWidth: 1,
      borderBottomColor: '#f2f2f2',
      paddingBottom: 5
  },
  actionError: {
      flexDirection: 'row',
      marginTop: 10,
      borderBottomWidth: 1,
      borderBottomColor: '#FF0000',
      paddingBottom: 5
  },
  textInput: {
      flex: 1,
      marginTop: Platform.OS === 'ios' ? 0 : -5,
      paddingLeft: 10,
      color: '#05375a',
  },
  errorMsg: {
      color: '#FF0000',
      fontSize: 14,
  },
  button: {
      alignItems: 'center',
      marginTop: 40
  },
  signIn: {
      width: '100%',
      height: 50,
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 10
  },
  textSign: {
      fontSize: 18,
      fontWeight: 'bold'
  }
})