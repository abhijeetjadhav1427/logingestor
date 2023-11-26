import axios from 'axios';
import * as actions from './ActionType';
import { baseUrl } from './baseUrl';

export const searchSuccess = (data) => ({
    type: actions.SEARCH_SUCCESS,
    data
});
export const searchFail = (error) => ({
    type: actions.SEARCH_FAIL,
    error
});

export const search = (filters) => (dispatch) => {
    axios.get(baseUrl + "/logs/search", {
        params: filters
    }).then(response => {
        console.log(response.data);
        dispatch(searchSuccess(response.data));
    }).catch(e => {
        dispatch(searchFail(e));
    });
};