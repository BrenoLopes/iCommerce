/*
{
  "products": [
    {
      "id": 2,
      "name": "Tomate",
      "description": "Um tomate bem verde",
      "price": 1,
      "vendor": "Admin",
      "image": "/api/image/U8G9PupjZWMDwIjO5suAcyL6CFHVzp5EwrAQ5jsJ.jpeg",
      "category_id": 8,
      "category_name": "Comida"
    }
  ]
}
*/

export interface ProductResponse {
  products: Product[]
}

export interface Product {
  id: number,
  name: string,
  description: string,
  price: number,
  vendor: string,
  image: string,
  category_id: number,
  category_name: string,
}
