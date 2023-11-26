import * as actions from "./ActionType";

const initialState = {
    searches: null,
    error: null
};

const searchSuccess = (state, action) => ({
    ...state,
    error: null,
    searches: action.data
});
const searchFail = (state, action) => ({
    ...state,
    error: action.error,
    searches: null
});

export const searchReducer = (state = initialState, action) => {
    
    switch (action.type) {
        case actions.SEARCH_SUCCESS:
            return searchSuccess(state, action);
        case actions.SEARCH_FAIL:
            return searchFail(state, action);
        default:
            return state
    }
};