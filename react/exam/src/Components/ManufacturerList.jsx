import React, {useState, useEffect} from "react";
import Loader from '../UI/Loader/Loader';
import ManufacturerService from "../API/ManufacturerService";
import {Button, Modal} from "react-bootstrap";
import ManufacturerForm from "./ManufacturerForm";
import ManufacturerItem from "./ManufacturerItem";
import PriceForm from "./PriceForm";
const ManufacturerList = () => {
    const [manufacturers, setManufacturers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);
    const [cheapestForm, setCheapestForm] = useState(false);
    useEffect(() => {
        getAll();
    }, []);

    const getAll = () => {
        setLoading(true);
        setTimeout(() => {
            ManufacturerService.getAll().then(data => setManufacturers(data));
            setLoading(false);
        }, 1000);
    }

    const getCheapest = (data) => {
        setLoading(true);
        setTimeout(() => {
            setManufacturers(data);
            setCheapestForm(false);
            setLoading(false);
        }, 1000);
    }

    const addManufacturer = (manufacturer) => {
        setManufacturers([...manufacturers, manufacturer]);
        setModal(false);
    }

    const removeManufacturer = (id) => {
        ManufacturerService.removeManufacturer(id);
        setManufacturers(manufacturers.filter(m => m.id !== id));
    }

    const updateManufacturer = (manufacturer) => {
        setManufacturers([...manufacturers.filter(m => m.id !== manufacturer.id), manufacturer]);
    }
    return (<div>
        {loading ? (<div>
            <Loader/>
        </div>) : (<div>
            {manufacturers.length ? (<div>
                {manufacturers.map(manufacturer => <div key={manufacturer.id}>
                    <ManufacturerItem manufacturer={manufacturer}
                                      removeManufacturer={removeManufacturer}
                                      updateManufacturer={updateManufacturer}/>
                </div>)}
            </div>) : (<div>
                <h1> manufacturers not founded! </h1>
            </div>)}
            <Button onClick={() => setModal(true)}> add manufacturer</Button>
            <Modal show={modal} onHide={setModal}> <ManufacturerForm CreateOrUpdate={addManufacturer}/> </Modal>
            <Button onClick={() => getAll()}> all </Button>
            <Button onClick={() => setCheapestForm(true)}> get cheapest then price </Button>
            <Modal show={cheapestForm} onHide={setCheapestForm}><PriceForm getCheapest={getCheapest}/></Modal>
        </div>)}
    </div>);
};
export default ManufacturerList;
