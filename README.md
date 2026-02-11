🛒 MFCommerce

Sistema de e-commerce desenvolvido em Java com Spring Boot, utilizando JPA e Hibernate para mapeamento objeto-relacional (ORM).

Projeto desenvolvido por Marcel Finavaro
💻 Desenvolvedor Full Stack
🔗 Repositório: https://github.com/marcelfinavaro/mfcommerce.git

📚 Sobre o Projeto

O MFCommerce é uma aplicação back-end construída como parte da formação Desenvolvedor Moderno, no módulo de Back-end, com foco em:

Modelagem de domínio

Mapeamento Objeto-Relacional (ORM)

Relacionamentos entre entidades

Configuração de banco de dados em memória (H2)

Seeding de base de dados

Boas práticas com JPA e Hibernate

O sistema simula um cenário real de e-commerce com usuários, pedidos, produtos, categorias e pagamentos.

🗄️ Banco de Dados

O projeto utiliza o banco de dados H2 em memória, ideal para testes e desenvolvimento.

🔹 Configuração do H2
# Dados de conexão com o banco H2
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.profiles.active=test
spring.jpa.open-in-view=false

Acesse o console H2 via:
👉 http://localhost:8080/h2-console

⏳ Trabalhando com Datas

Recomendação para uso do tipo Instant:

@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
private Instant moment;

🔗 Relacionamentos JPA Implementados
🔹 Muitos-para-Um (One-to-Many / Many-to-One)

Um usuário pode ter vários pedidos:

@OneToMany(mappedBy = "client")
private List<Order> orders = new ArrayList<>();

@ManyToOne
@JoinColumn(name = "client_id")
private User client;

🔹 Um-para-Um (One-to-One)

Pedido e pagamento:

@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
private Payment payment;

@OneToOne
@MapsId
private Order order;

🔹 Muitos-para-Muitos (Many-to-Many)

Produto e categoria:

@ManyToMany
@JoinTable(name = "tb_product_category",
joinColumns = @JoinColumn(name = "product_id"),
inverseJoinColumns = @JoinColumn(name = "category_id"))
private Set<Category> categories = new HashSet<>();

🔹 Muitos-para-Muitos com Classe de Associação

Implementado através da entidade OrderItem, utilizando chave composta:
@EmbeddedId
private OrderItemPK id = new OrderItemPK();

Relacionamento em Order:

@OneToMany(mappedBy = "id.order")
private Set<OrderItem> items = new HashSet<>();

Relacionamento em Product:
@OneToMany(mappedBy = "id.product")
private Set<OrderItem> items = new HashSet<>();

Esse modelo permite armazenar atributos adicionais na relação, como:

Quantidade

Preço

🌱 Seeding da Base de Dados

A base de dados é populada automaticamente através do arquivo:

src/main/resources/import.sql


🚀 Tecnologias Utilizadas

Java 17+

Spring Boot

Spring Data JPA

Hibernate

H2 Database

Maven


Marcel Finavaro
Desenvolvedor Full Stack

🔗 GitHub: https://github.com/marcelfinavaro

📦 Projeto: https://github.com/marcelfinavaro/mfcommerce.git












