import react, {useEffect, useState} from 'react'
import { useHistory } from 'react-router-dom';
import NavigationComponent from "../../components/navigation/NavigationComponent";

import './ProductsPage.scss'
import ProductThumbnailComponent from "../../components/productthumbnail/ProductThumbnailComponent";
import Network, {getFullUrl} from "../../networking/url";
import {Product, ProductResponse} from "./types";
import axios from "axios";

const ProductsPage: react.FC = () => {
  const [thumbnails, setThumbnails] = useState<JSX.Element[]>([])
  const products = new Map<number, Product>()

  const history = useHistory()

  useEffect(() => {
    loadProducts().then()
  }, [])

  const loadProducts = async () => {
    try {
      const token = localStorage.getItem('token')

      const { data } = await axios.get<ProductResponse>(getFullUrl('/api/product'), {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      })

      data.products.forEach(p => {
        products.set(p.id, p)
      })

      const buffer: JSX.Element[] = []
      products.forEach((p) => {
        buffer.push(
          <ProductThumbnailComponent
            imageUrl={getFullUrl(p.image)}
            productName={p.name}
            productPrice={p.price}
            onClick={() => navigateToProduct(p.id)}
            key={p.id}
          />
        )
      })

      setThumbnails(buffer)
    } catch (e) {
      localStorage.removeItem('token')
      history.push("/")
      // localStorage.removeItem('token')
      // history.push('/')
    }
  }

  const navigateToProduct: (id: number) => void = (id) => {
    const product = products.get(id)

    if (product)
      console.log("Navegando para ", product.name, "with price ", product.price);
  }

  return (
    <div className="product-catalog-container">
      <NavigationComponent />
      <div className="products-list-container">
        {thumbnails}
      </div>
    </div>
  )
}

export default ProductsPage
