import { NavigationContainer } from '@react-navigation/native'
import { CardStyleInterpolators, createStackNavigator } from '@react-navigation/stack'
import { Colors } from './constants/Colors'
import { Home } from './screens/Home/Home'
import { TodoDetails } from './screens/TodoDetails/TodoDetails'
import { SafeAreaView, StyleSheet, View, Text, Image, TouchableOpacity, Animated, Switch} from 'react-native'
import HomeIcon from './assets/home.png'
import AboutIcon from './assets/settings.png'
import LogoutIcon from './assets/logout.png'
import Menu from './assets/menu.png'
import MenuClose from './assets/close.png'
import { useState, useRef, useEffect } from 'react'
import { Edit } from './screens/Edit/Edit'
import { Create } from './screens/Create/Create'
import { About } from './screens/About/About'
import { Login } from './screens/Login/Login'
import { Register } from './screens/Register/Register'
import { auth } from './firebase/firebase'

const Stack = createStackNavigator()
const AuthStack = createStackNavigator()


const AuthScreens = () => {
  return (
      <AuthStack.Navigator  screenOptions={{
        headerShown: false,
        gestureEnabled: true,
        gestureDirection: 'horizontal',
        cardStyleInterpolator: CardStyleInterpolators.forModalPresentationIOS
      }}
      >
          <AuthStack.Screen name="Login" component={Login}/>
          <AuthStack.Screen name="Register" component={Register}/>
      </AuthStack.Navigator>
  )
}


const Screen = ({currTab}) => {
  return (
    <Stack.Navigator screenOptions={{
      gestureEnabled: true,
      gestureDirection: 'horizontal',
      cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS
    }}>
            {currTab === 'Home' ? 
            <Stack.Screen name='Home' component={Home}    
              options={() => {
                return ({
                  headerStyle: {
                    backgroundColor: Colors.white
                  },
                  headerTintColor: Colors.black
                })
              }}/> :
            <Stack.Screen name='About' component={About}
              options={() => {
                return ({
                  headerStyle: {
                    backgroundColor: Colors.white
                  },
                  headerTintColor: Colors.black
                })
              }}
            /> 
            }
            <Stack.Screen 
              name="Tododetails" 
              component={TodoDetails}
              options={({route}) => {
                return ({
                  title: route.params.title,
                  headerStyle: {
                    backgroundColor: route.params.color
                  },
                  headerTintColor: 'white'
                })
              }}
            />
            <Stack.Screen 
              name="Edit Todo" 
              component={Edit}
              options={({route}) => {
                return ({
                  title:`Edit ${route.params.title} list`,
                  headerStyle: {
                    backgroundColor: route.params.color
                  },
                  headerTintColor: 'white'
                })
              }}
            />
            <Stack.Screen 
              name="Create Todo" 
              component={Create}
              options={({route}) => {
                return ({
                  title:'Create new list',
                  headerStyle: {
                    backgroundColor: route.params.color ? route.params.color : Colors.primary
                  },
                  headerTintColor: 'white'
                })
              }}
            />
          </Stack.Navigator>
  )
}

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(true)
  const [currTab, setCurrTab] = useState('Home')
  const [showMenu, setShowMenu] = useState(false)
  const [isEnabled, setIsEnabled] = useState(false);
  const toggleSwitch = () => {
    setIsEnabled(previousState => !previousState)
  }

  useEffect(() => {
    if(auth.currentUser) {
      setIsAuthenticated(true)
    }
    auth.onAuthStateChanged((user) => {
      if (user) {
          setIsAuthenticated(true);
      } else {
          setIsAuthenticated(false);
      }
    })
  }, [auth])

  const offsetValue = useRef(new Animated.Value(0)).current
  const scaleValue = useRef(new Animated.Value(1)).current
  const closeButtonOffset = useRef(new Animated.Value(0)).current

  const handle = () => {
    Animated.timing(scaleValue, {
      toValue: showMenu ? 1 : 0.88,
      duration: 300,
      useNativeDriver: true
    })
      .start()

    Animated.timing(closeButtonOffset, {
      toValue: 0,
      duration: 300,
      useNativeDriver: true
    })
      .start()

    Animated.timing(offsetValue, {
      toValue: showMenu ? 0 : 220,
      duration: 300,
      useNativeDriver: true
    })
      .start()    

    setShowMenu(!showMenu)
  }

  return (
    <SafeAreaView style={styles.container}>
      <View style={{justifyContent:'flex-start', padding: 20}}>
        <Text style={styles.title}>TODOS APP</Text>
        <View style={{ flexGrow: 1, marginTop: 50 }}>
          {TabButton(currTab, setCurrTab, 'Home', HomeIcon, setIsAuthenticated, setShowMenu, handle)}
          {TabButton(currTab, setCurrTab, 'About', AboutIcon, setIsAuthenticated, setShowMenu, handle)}
        </View>
        <View>
          {TabButton(currTab, setCurrTab, 'LogOut', LogoutIcon, setIsAuthenticated, setShowMenu, handle)}
        </View>
      </View>
      <Animated.View style={{
        flexGrow: 1,
        backgroundColor: Colors.white,
        position: 'absolute',
        top: 0,
        bottom: 0,
        left: 0,
        right: 0,
        borderRadius: showMenu ? 20 : 0,
        transform: [
          { scale: scaleValue },
          { translateX: offsetValue }
        ]
      }}>
      {isAuthenticated &&
        <Animated.View style={{
          transform: [{
            translateY: closeButtonOffset
          }]
        }}>
        <View style={{flexDirection: 'row',  alignContent: 'center', justifyContent: 'space-between'}}>
          <TouchableOpacity 
            onPress={handle}
          >
            <Image source={showMenu ? MenuClose: Menu} style={{
                marginLeft: 15,
                width: 25,
                height: 25,
                tintColor: Colors.primary,
                marginTop: 40,
                marginBottom: 10
            }}></Image>
          </TouchableOpacity>
        </View>
        </Animated.View>
      }
        <NavigationContainer >
          {!isAuthenticated ? <AuthScreens  /> : <Screen currTab={currTab}/>}
        </NavigationContainer> 
      </Animated.View>
    </SafeAreaView>
  )
}

const TabButton = (currTab, setCurrTab, title, image, setIsAuthenticated, setShowMenu, handle) => {
  return (
    <TouchableOpacity onPress={() => {
      if (title == "LogOut") {
        setIsAuthenticated(false)
        setShowMenu(false)
        handle()
        auth.signOut()
      } else {
        setCurrTab(title)
        setShowMenu(false)
        handle()
      }
    }}>
      <View style={{flexDirection: 'row', alignItems: 'center',  backgroundColor: currTab === title ? 'white' : 'transparent',  marginTop: 15, paddingLeft: 20, paddingRight: 40, paddingVertical: 8, borderRadius: 8}}>
        <Image source={image} style={{width: 25, height: 25, tintColor: currTab === title ? Colors.primary : "white"}}/>
        <Text style={{fontSize: 15, fontWeight: 'bold', paddingLeft: 15, color: currTab === title ? Colors.primary   : "white"}}>{title}</Text>
      </View>
    </TouchableOpacity>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.primary,
    alignItems: 'flex-start',
    justifyContent: 'flex-start',
  },
  title: {
    color: '#ffffff',
    fontSize: 25,
    fontWeight: '700',
    marginTop: 80
  }
})

