import { View, FlatList, TouchableOpacity} from "react-native"
import { Colors } from "../../constants/Colors"
import { Todo } from "../../features/Todo/Todo"
import { styles } from "./Home.style"
import { Ionicons } from '@expo/vector-icons'
import {useState, useEffect} from 'react'
import { auth, db } from "../../firebase/firebase"
import { collection, deleteDoc, doc, getDoc, getDocs, setDoc } from 'firebase/firestore/lite'
import Spinner from 'react-native-loading-spinner-overlay'
import AsyncStorage from '@react-native-async-storage/async-storage'

export const Home = ({navigation}) => {
  const [listTodos, setListTodos] = useState([])
  
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const getData = async () => {   
      try { 
        setLoading(true) 
        const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos`))
        const listTodosNew = []
        Data.forEach((doc) => {
            listTodosNew.push({
              ...doc.data(),
              key: doc.id
            })
        })
        setLoading(false) 
        const jsonValue = JSON.stringify(listTodosNew)
        await AsyncStorage.setItem('data', jsonValue)
        return setListTodos(listTodosNew)
      } catch(err) {
        setLoading(false) 
        const res = await AsyncStorage.getItem('data')
        return setListTodos(res != null ? JSON.parse(res) : null)
      }
    }
    getData()
  }, [])



  const addItem =(item) => {
    const myDoc = doc(db, `users/${auth.currentUser?.uid}/Todos`, `${Math.random()}`)

    setDoc(myDoc, item)
      .then(() => {
        const getData = async () => {     
          setLoading(true) 
          const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos`))
          const listTodosNew = []
          Data.forEach((doc) => {
              listTodosNew.push({
                ...doc.data(),
                key: doc.id
              })
          })
          setLoading(false) 
      
          return setListTodos(listTodosNew)
        }
        
        getData()
      })
      .catch(err => {
        alert(err)
      })
  }

  const updateItemFromLists = (item, propKey) => {
    const myDoc = doc(db, `users/${auth?.currentUser?.uid}/Todos/`, propKey)

    setDoc(myDoc, item, {merge: true})
    .then(() => {
      const getData = async () => {     
        setLoading(true) 
        const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos`))
        const listTodosNew = []
        Data.forEach((doc) => {
            listTodosNew.push({
              ...doc.data(),
              key: doc.id
            })
        })
        setLoading(false) 
    
        return setListTodos(listTodosNew)
      }
      
      getData()
    })
    .catch(err => {
      alert(err)
    })
  }
  
  const removeItem =(propKey) => {
    const myDoc = doc(db, `users/${auth?.currentUser?.uid}/Todos/`, propKey)

    deleteDoc(myDoc)
    .then(() => {
      const getData = async () => {     
        setLoading(true) 
        const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos`))
        const listTodosNew = []
        Data.forEach((doc) => {
            listTodosNew.push({
              ...doc.data(),
              key: doc.id
            })
        })
        setLoading(false) 
        alert('Deleted Complete.')
    
        return setListTodos(listTodosNew)
      }
      
      getData()
    })
    .catch(err => {
      alert(err)
    })
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
      <FlatList
        data={listTodos}
        renderItem={({item: {title, color, key}, index}) => {
          return <Todo title={title} color={color} navigation={navigation} index={index} propKey={key} updateItemFromLists={updateItemFromLists} removeItem={removeItem}/>
        }}
       
      />
      <TouchableOpacity style={styles.addIcon} onPress={() => {navigation.navigate('Create Todo', {saveCreate: (item) => addItem(item)})}}>
          <Ionicons name="ios-add" size={40} color="white"/>
      </TouchableOpacity>
    </View>
  )}