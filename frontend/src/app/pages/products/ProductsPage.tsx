import react, {useEffect, useState} from 'react'
import { useHistory } from 'react-router-dom';
import NavigationComponent from "../../components/navigation/NavigationComponent";

import './ProductsPage.scss'
import ProductThumbnailComponent from "../../components/productthumbnail/ProductThumbnailComponent";

interface Product {
  imageUrl: string
  productName: string
  productPrice: number
}

const ProductsPage: react.FC = () => {
  const [thumbnails, setThumbnails] = useState<JSX.Element[]>([])
  const products = new Map<number, Product>()

  useEffect(() => {
    products.set(1, {
      imageUrl: "https://a-static.mlcdn.com.br/618x463/creme-de-leite-integral-piracanjuba-200g/magazineluiza/226146500/4785e4a4398f4f80755372eb3111d32b.jpg",
      productName: "Creme de Leite Integral Piracanjuba 200g",
      productPrice: 3.59
    })
    products.set(2, {
      imageUrl: "https://a-static.mlcdn.com.br/618x463/creme-de-leite-integral-piracanjuba-200g/magazineluiza/226146500/4785e4a4398f4f80755372eb3111d32b.jpg",
      productName: "Creme de Leite Integral Piracanjuba 200g",
      productPrice: 3.59
    })
    products.set(3, {
      imageUrl: "https://a-static.mlcdn.com.br/618x463/creme-de-leite-integral-piracanjuba-200g/magazineluiza/226146500/4785e4a4398f4f80755372eb3111d32b.jpg",
      productName: "Creme de Leite Integral Piracanjuba 200g",
      productPrice: 3.59
    })
    products.set(4, {
      imageUrl: "https://a-static.mlcdn.com.br/618x463/creme-de-leite-integral-piracanjuba-200g/magazineluiza/226146500/4785e4a4398f4f80755372eb3111d32b.jpg",
      productName: "Creme de Leite Integral Piracanjuba 200g",
      productPrice: 3.59
    })
    products.set(5, {
      imageUrl: "https://a-static.mlcdn.com.br/618x463/creme-de-leite-integral-piracanjuba-200g/magazineluiza/226146500/4785e4a4398f4f80755372eb3111d32b.jpg",
      productName: "Creme de Leite Integral Piracanjuba 200g",
      productPrice: 3.59
    })
    products.set(6, {
      imageUrl: "https://a-static.mlcdn.com.br/618x463/creme-de-leite-integral-piracanjuba-200g/magazineluiza/226146500/4785e4a4398f4f80755372eb3111d32b.jpg",
      productName: "Creme de Leite Integral Piracanjuba 200g",
      productPrice: 3.59
    })

    const buffer: JSX.Element[] = []

    products.forEach((p, id) => {
      buffer.push(
        <ProductThumbnailComponent
          imageUrl={p.imageUrl}
          productName={p.productName}
          productPrice={p.productPrice}
          onClick={() => navigateToProduct(id)}
        />
      )
    })

    setThumbnails(buffer)
  }, [products])

  const navigateToProduct: (id: number) => void = (id) => {
    const product = products.get(id)

    if (product)
      console.log("Navegando para ", product.productName, "with price ", product.productPrice);
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
