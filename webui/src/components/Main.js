import React, { useReducer, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { search } from "../redux/searchAction";

export default function Main() {
    const searches = useSelector(state => state.searchResult.searches);
    const error = useSelector(state => state.searchResult.error);
    const dispatch = useDispatch();

    const [formData, setFormData] = useState({
        level: '',
        message: '',
        resourceId: '',
        timestamp: '',
        fromDate: '',
        toDate: '',
        traceId: '',
        spanId: '',
        commit: '',
        parentResourceId: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const { fromDate, toDate } = formData;

        const isValidDate = (dateString) => {
            const date = new Date(dateString);
            return date instanceof Date && !isNaN(date);
        };

        if ((fromDate || toDate) && (!fromDate || !toDate || !isValidDate(fromDate) || !isValidDate(toDate))) {
            alert('Both From Date and To Date are required and should be valid dates');
            return; // Prevent form submission if validation fails
        }

        if (fromDate && toDate && new Date(fromDate) > new Date(toDate)) {
            alert('From Date cannot be greater than To Date');
            return; // Prevent form submission if validation fails
        }

        const filteredParams = Object.fromEntries(
            Object.entries(formData).filter(([_, value]) => value !== '' && value !== null)
        );
        dispatch(search(filteredParams));
    };

    return (
        <div>
            <br />
            <br />
            <br />
            <form className="form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="level">Level:</label>
                    <input
                        type="text"
                        name="level"
                        id="level"
                        value={formData.level}
                        onChange={handleChange}
                        placeholder="Level"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="message">Message:</label>
                    <input
                        type="text"
                        name="message"
                        id="message"
                        value={formData.message}
                        onChange={handleChange}
                        placeholder="Message"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="resourceId">resourceId:</label>
                    <input
                        type="text"
                        name="resourceId"
                        id="resourceId"
                        value={formData.resourceId}
                        onChange={handleChange}
                        placeholder="resourceId"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="timestamp">timestamp:</label>
                    <input
                        type="datetime-local"
                        name="timestamp"
                        id="timestamp"
                        value={formData.timestamp}
                        onChange={handleChange}
                        placeholder="timestamp"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="fromDate">fromDate:</label>
                    <input
                        type="datetime-local"
                        name="fromDate"
                        id="fromDate"
                        value={formData.fromDate}
                        onChange={handleChange}
                        placeholder="fromDate"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="toDate">toDate:</label>
                    <input
                        type="datetime-local"
                        name="toDate"
                        id="toDate"
                        value={formData.toDate}
                        onChange={handleChange}
                        placeholder="toDate"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="traceId">traceId:</label>
                    <input
                        type="text"
                        name="traceId"
                        id="traceId"
                        value={formData.traceId}
                        onChange={handleChange}
                        placeholder="traceId"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="spanId">spanId:</label>
                    <input
                        type="text"
                        name="spanId"
                        id="spanId"
                        value={formData.spanId}
                        onChange={handleChange}
                        placeholder="spanId"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="commit">commit:</label>
                    <input
                        type="text"
                        name="commit"
                        id="commit"
                        value={formData.commit}
                        onChange={handleChange}
                        placeholder="commit"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="parentResourceId">parentResourceId:</label>
                    <input
                        type="text"
                        name="parentResourceId"
                        id="parentResourceId"
                        value={formData.parentResourceId}
                        onChange={handleChange}
                        placeholder="parentResourceId"
                    />
                </div>
                <button type="submit">Submit</button>
            </form>
            <br />
            <br />
            <div style={{ marginLeft: "10em" }}>
                ${error == null ? "" :
                    <pre>
                        ${JSON.stringify(error, null, 2)}
                    </pre>
                }
            </div>
            <br />
            <br />
            <div style={{ marginLeft: "10em" }}>
                ${searches == null ? "" :
                    <pre>
                        ${JSON.stringify(searches, null, 4)}
                    </pre>
                }
            </div>
        </div>
    )
}