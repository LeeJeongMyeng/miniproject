import {createStore} from "vuex";

createStore({
    state(){
        return{
            user:{}
        }
    },
    metations:{
        setUser(state,value){
            state.user = value;
        }
    }
})