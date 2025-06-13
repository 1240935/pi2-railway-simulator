# US30 - Curve Fitting and Correlation Analysis

## Objectives

The primary objective of this user story (US30) is to apply curve fitting techniques to the experimental data obtained from execution time measurements (obtained in US29). The goal is to determine which theoretical model (e.g., linear, quadratic, polynomial) best fits the observed data and to evaluate the strength of that fit using correlation analysis.

This analysis enables the validation of expected time complexity based on actual runtime behavior for the procedures implemented in US13 and US14.

---

## Theoretical Concepts

- **Curve Fitting:**  
  The process of constructing a curve (such as a polynomial or linear function) that best approximates a set of data points. This is used to estimate the underlying relationship between input size and execution time.

- **Polynomial Regression:**  
  A type of regression analysis where the relationship between the independent variable \( x \) (input size) and the dependent variable \( y \) (execution time) is modeled as a polynomial of degree \( d \). The general form is:

  $$
  y = a_0 + a_1x + a_2x^2 + \cdots + a_dx^d
  $$

- **Correlation Coefficient (\( r \)):**  
  The Pearson correlation coefficient measures the strength and direction of a linear relationship between two variables. It is defined as:

  $$
  r = \frac{\sum (x_i - \bar{x})(y_i - \bar{y})}
  {\sqrt{\sum (x_i - \bar{x})^2 \cdot \sum (y_i - \bar{y})^2}}
  $$

  ### Interpretation:

  | Correlation Coefficient \( r \) | Strength & Direction |
      |---------------------------------|-----------------------|
  | \( r = 1 \)                     | Perfect positive correlation |
  | \( 0.8 < r < 1 \)               | Strong positive correlation |
  | \( 0.5 < r < 0.8 \)             | Moderate positive correlation |
  | \( 0.1 < r < 0.5 \)             | Weak positive correlation |
  | \( 0 < r < 0.1 \)               | Very weak positive correlation |
  | \( r = 0 \)                     | No correlation |
  | \( -0.1 < r < 0 \)              | Very weak negative correlation |
  | \( -0.5 < r < -0.1 \)           | Weak negative correlation |
  | \( -0.8 < r < -0.5 \)           | Moderate negative correlation |
  | \( -1 < r < -0.8 \)             | Strong negative correlation |
  | \( r = -1 \)                    | Perfect negative correlation |
- **Model Selection:**  
  By comparing the value of \( R \) for different degrees of polynomial regression (e.g., linear vs. quadratic or higher), and using MDISC as an evaluation criterion, we can determine which model best fits the observed data.

---

## Methodology

To fulfill US30, the following methodology was applied:

1. Execution time data for various input sizes was collected from US29 for both **US13** and **US14**.
2. Polynomial regression models of varying degrees (e.g., degree 3 for US13, degree 9 for US14) were fitted using `scikit-learn`'s `PolynomialFeatures` and `LinearRegression`.
3. For each model, the **Pearson correlation coefficient** \( R \) was calculated between the predicted values and the actual observed times.
4. The US with the **strongest correlation** (highest \( R \) value) was identified as having the best-fitting model.
5. Graphs were generated to visually confirm the accuracy and shape of the fitted curves against the actual data points.

---

This analysis provides insights into the complexity and scalability of different algorithmic procedures, and supports the identification of performance patterns consistent with theoretical expectations.

---


## Analysis and Interpretation of Results

To analyze the asymptotic behavior of the execution times collected for **US13** and **US14**, polynomial regression models were applied to both datasets.

Instead of limiting the models to linear or quadratic functions, higher-degree **polynomial fits** were tested to evaluate which best approximates the observed execution times. The quality of each model was measured using the **Pearson correlation coefficient (R)**.

### 🔹 US13 - Polynomial Degree 3

- A 3rd-degree polynomial was applied to the execution time data of US13.
- The model fit was strong, with an \( R \) value of approximately `0.9863`, indicating an excellent match.
- The curve smoothly follows the general upward trend of the data while capturing slight nonlinearities, suggesting that the execution time increases at a rate that is not perfectly quadratic but still polynomial in nature.

### 🔹 US14 - Polynomial Degree 9

- A 9th-degree polynomial was tested for US14 due to the visibly more erratic and sharp increases in execution time at higher input sizes.
- This higher-degree model achieved a near-perfect correlation ( approx 1 \), closely tracking the complex shape of the data.

---

## Summary

- These insights help justify the theoretical complexity assessments and provide a clearer understanding of the performance expectations for each user story. Polynomial regression allows for a more flexible analysis of execution time behavior and is useful when the data does not align cleanly with standard linear models.

# Distribuição Percentual entre Membros do Grupo

Abaixo está a divisão de 100% do trabalho entre os quatro integrantes do grupo:

- **1240934 – Francisco Vasconcelos**: 35%
- **1240935 – Gabriel Azevedo**: 20%
- **1240936 – Gonçalo Azevedo**: 20%
- **1240941 – Paulo Ferreira**: 25%

O quinto membro do grupo não compareceu/participou no projeto.