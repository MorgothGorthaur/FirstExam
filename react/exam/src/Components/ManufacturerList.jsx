import React, {useState, useEffect} from "react";
import Loader from '../UI/Loader/Loader';
import ManufacturerService from "../API/ManufacturerService";
import {Button, Modal} from "react-bootstrap";
import ManufacturerForm from "./ManufacturerForm";
const ManufacturerList = () => {
    const [manufacturers, setManufacturers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(true);
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

    const addManufacturer = (manufacturer) =>{
        setManufacturers([...manufacturers, manufacturer]);
        setModal(false);
    }
    return (
        <div>
            {loading ? (
                <div>
                    <Loader/>
                </div>
            ) : (
                <div>
                    {manufacturers.length ? (
                        <div>
                            {manufacturers.map(manufacturer =>
                                <div>
                                    <h1> g </h1>
                                </div>
                            )
                            }
                        </div>
                    ) : (
                        <div>
                            <h1> manufacturers not founded! </h1>
                        </div>
                    )}
                    <Button onClick={() => setModal(true)}> add manufacturer</Button>
                    <Modal show={modal} onHide={setModal}> <ManufacturerForm CreateOrUpdate={addManufacturer} /> </Modal>
                </div>
            )}
        </div>
    );
};
export default ManufacturerList;
