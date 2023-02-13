import React, {useState, useEffect} from "react";
import Loader from '../UI/Loader/Loader';
import ManufacturerService from "../API/ManufacturerService";
const ManufacturerList = () => {
    const [manufacturers, setManufacturers] = useState();
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            fetchManufacturers();
            setLoading(false);
        }, 1000);
    }, []);

    async function fetchManufacturers() {
        setManufacturers(await ManufacturerService.getAll());
    }

    return (
        <div>
            {
                loading ? (
                    <div>
                        <Loader/>
                    </div>
                ) : (
                    <div>
                    </div>
                )
            }
        </div>
    );
};
export default ManufacturerList;
