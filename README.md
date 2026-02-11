
Marcel Finavaro
Desenvolvedor Full Stack Java

https://github.com/marcelfinavaro/mfcommerce.git


📋 Sobre o Projeto
Este projeto foi desenvolvido durante o Capítulo: Modelo de domínio e ORM do módulo Back end da Formação Desenvolvedor Moderno da DevSuperior.

O DSCommerce é uma aplicação backend que implementa um sistema de e-commerce completo, focando no mapeamento objeto-relacional (ORM) utilizando JPA/Hibernate. O projeto demonstra na prática a implementação de relacionamentos complexos entre entidades e boas práticas de modelagem de dados.

🎯 Objetivos de Aprendizado
✅ Revisão de Álgebra Relacional e SQL

✅ Comparação entre JDBC puro vs ORM

✅ Fundamentos de ORM com JPA e Hibernate

✅ Implementação de relacionamentos no banco de dados

✅ Mapeamento objeto-relacional com Spring Data JPA

✅ Seed de dados com import.sql

🗂️ Modelo de Domínio
text
┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│    User     │1    N│    Order    │1    1│   Payment   │
│─────────────│──────│─────────────│──────│─────────────│
│ id          │      │ id          │      │ id          │
│ name        │      │ moment      │      │ moment      │
│ email       │      │ client ◄────┼──────┤ order       │
│ phone       │      │ payment     │      └─────────────┘
│ password    │      └─────────────┘
│ roles       │            │ 1
└─────────────┘            │
                           │ N
                    ┌──────▼──────┐     ┌─────────────┐
                    │  OrderItem  │N   1│   Product   │
                    │─────────────│─────│─────────────│
                    │ quantity    │     │ id          │
                    │ price       │     │ name        │
                    │ order ◄─────┼─────┤ price       │
                    │ product ◄───┼─────┤ description │
                    └─────────────┘     │ img_url     │
                                        │ categories  │
                                        └─────────────┘
                                              │
                                              │ N
                                        ┌─────▼─────┐
                                        │ Category  │
                                        │───────────│
                                        │ id        │
                                        │ name      │
                                        └───────────┘
🔗 Relacionamentos Implementados
📌 Muitos-para-Um (Many-to-One)
java
@Entity
@Table(name = "tb_order")
public class Order {
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
}
📌 Um-para-Um (One-to-One)
java
@Entity
@Table(name = "tb_payment")
public class Payment {
    @OneToOne
    @MapsId
    private Order order;
}
📌 Muitos-para-Muitos (Many-to-Many)
java
@Entity
@Table(name = "tb_product")
public class Product {
    @ManyToMany
    @JoinTable(
        name = "tb_product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
}
📌 Muitos-para-Muitos com Classe de Associação
java
@Embeddable
public class OrderItemPK {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

@Entity
@Table(name = "tb_order_item")
public class OrderItem {
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();
    private Integer quantity;
    private Double price;
    
    public OrderItem(Order order, Product product, Integer quantity, Double price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }
}
📌 Métodos auxiliares nas entidades
java
public class Order {
    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();
    
    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
}

public class Product {
    @OneToMany(mappedBy = "id.product")
    private Set<OrderItem> items = new HashSet<>();
    
    public List<Order> getOrders() {
        return items.stream().map(x -> x.getOrder()).toList();
    }
}
🛠️ Tecnologias Utilizadas
Java 17 - Linguagem de programação

Spring Boot 3.1 - Framework para aplicações Java

Spring Data JPA - Abstração para acesso a dados

Hibernate - Implementação do JPA

H2 Database - Banco de dados em memória

Maven - Gerenciamento de dependências

⚙️ Configuração do Banco de Dados
properties
# Dados de conexão com o banco H2
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# H2 Client
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA, SQL
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.profiles.active=test
spring.jpa.open-in-view=false
💡 Destaques Técnicos
🕐 Tratamento de Instant
java
@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
private Instant moment;
🌱 Seeding da Base de Dados (import.sql)
sql
-- Inserindo usuários
INSERT INTO tb_user (name, email, phone, password) VALUES ('Maria Brown', 'maria@gmail.com', '988887888', '123456');
INSERT INTO tb_user (name, email, phone, password) VALUES ('Alex Green', 'alex@gmail.com', '977777777', '123456');

-- Inserindo produtos
INSERT INTO tb_product (name, price, description, img_url) VALUES ('The Lord of the Rings', 90.5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Smart TV', 2190.0, 'Nulla eu imperdiet purus. Maecenas ante.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Macbook Pro', 1250.0, 'Nam eleifend maximus tortor, at mollis.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('PC Gamer', 1200.0, 'Donec aliquet odio ac rhoncus cursus.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Rails for Dummies', 100.99, 'Cras fringilla convallis sem vel faucibus.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/5-big.jpg');

-- Inserindo categorias
INSERT INTO tb_category (name) VALUES ('Livros');
INSERT INTO tb_category (name) VALUES ('Eletrônicos');
INSERT INTO tb_category (name) VALUES ('Computadores');

-- Relacionando produtos e categorias
INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (3, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (4, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (5, 1);
📚 Materiais de Estudo
Pré-requisitos abordados:
🎬 Revisão Álgebra Relacional e SQL

🎬 Super revisão de OO e SQL com Java e JDBC

🎬 Nivelamento ORM - JPA e Hibernate

🎬 Data-hora em Java (LocalDate, LocalDateTime e Instant)

Documentação oficial:
📄 Documento de requisitos do DSCommerce

🚀 Como Executar o Projeto
bash
# Clone o repositório
git clone https://github.com/marcelfinavaro/mfcommerce.git

# Entre no diretório
cd dscommerce

# Execute com Maven
./mvnw spring-boot:run

# Acesse o console H2
http://localhost:8080/h2-console
📊 Estrutura do Banco de Dados
O projeto gera automaticamente as seguintes tabelas:

Tabela	Descrição	Relacionamentos
tb_user	Usuários/clientes	One-to-Many com Order
tb_order	Pedidos	Many-to-One com User, One-to-One com Payment
tb_payment	Pagamentos	One-to-One com Order
tb_product	Produtos	Many-to-Many com Category
tb_category	Categorias	Many-to-Many com Product
tb_product_category	Junção Produto-Categoria	Tabela de associação
tb_order_item	Itens do pedido	Classe de associação com atributos
🏆 Competências Desenvolvidas
✔️ Modelagem de dados para sistemas complexos
✔️ Mapeamento Objeto-Relacional (ORM) com JPA/Hibernate
✔️ Implementação de relacionamentos: @OneToMany, @ManyToOne, @OneToOne, @ManyToMany
✔️ Chaves compostas com @EmbeddedId
✔️ Cascade types e estratégias de persistência
✔️ Seed automático com import.sql
✔️ Boas práticas de organização de código Java/Spring

📌 Conclusão
Este projeto representa minha evolução no desenvolvimento backend com Java e Spring, consolidando conceitos fundamentais de mapeamento objeto-relacional e modelagem de domínios ricos. A implementação do DSCommerce demonstra capacidade de traduzir requisitos de negócio em um modelo de dados eficiente e bem estruturado, utilizando as melhores práticas do ecossistema Spring.

