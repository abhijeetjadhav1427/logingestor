import React from 'react';
import { applyMiddleware, combineReducers, legacy_createStore as createStore } from 'redux';
import { searchReducer } from './redux/searchReducer';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import './App.css';
import Main from './components/Main';

const rootReducer = combineReducers({
  searchResult: searchReducer
});

const store = createStore(
  rootReducer, applyMiddleware(thunk)
);

function App() {
  return (
    <React.StrictMode>
      <Provider store={store}>
          <Main />
      </Provider>
    </React.StrictMode>
  );
}

export default App;
