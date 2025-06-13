# US31 - Cargo-Based Revenue Analysis

## Objectives

The main objective of this user story (US31) is to perform a **statistical analysis** to identify which type of cargo most significantly contributes to the revenue of a user-specified railway station in a given year. The analysis includes:

- Calculating the **correlation** between each type of cargo and the station’s revenue.
- Identifying the cargo with the **highest correlation** with revenue.
- Applying a **linear regression model** using the selected cargo as the independent variable and revenue as the dependent variable.
- Estimating the **expected revenue** for January of the following year, assuming a 10% increase in the selected cargo based on **December's** value.
- Providing both a **95% confidence interval** and a **95% prediction interval** for the estimated revenue.

This analysis supports the strategic planning of cargo logistics and forecasting within the simulation environment of the railway network.

---

## Theoretical Concepts

- **Correlation Coefficient (\( r \)):**  
  The Pearson correlation coefficient measures the strength and direction of a linear relationship between two variables. It is defined as:

  $$
  r = \frac{\sum (x_i - \bar{x})(y_i - \bar{y})}
  {\sqrt{\sum (x_i - \bar{x})^2 \cdot \sum (y_i - \bar{y})^2}}
  $$

  ### Interpretation:

  | Correlation Coefficient \( r \) | Strength & Direction              |
    |-------------------------------|-----------------------------------|
  | \( r = 1 \)                   | Perfect positive correlation      |
  | \( 0.8 < r < 1 \)             | Strong positive correlation       |
  | \( 0.5 < r < 0.8 \)           | Moderate positive correlation     |
  | \( 0.1 < r < 0.5 \)           | Weak positive correlation         |
  | \( 0 < r < 0.1 \)             | Very weak positive correlation    |
  | \( r = 0 \)                   | No correlation                    |
  | \( -0.1 < r < 0 \)            | Very weak negative correlation    |
  | \( -0.5 < r < -0.1 \)         | Weak negative correlation         |
  | \( -0.8 < r < -0.5 \)         | Moderate negative correlation     |
  | \( -1 < r < -0.8 \)           | Strong negative correlation       |
  | \( r = -1 \)                  | Perfect negative correlation      |

- **Linear Regression:**  
  A method to model the relationship between a dependent variable (revenue) and an independent variable (cargo volume). The model follows the equation:

  $$
  y = mx + b
  $$

  where:
    - \( m \): slope (impact per unit of cargo)
    - \( b \): intercept
    - \( x \): cargo value
    - \( y \): predicted revenue


- **Sum of Squared Errors (SQE)**

The sum of squared residuals is calculated as:

$$
SSE = \sum_{i=1}^n (y_i - \hat{y}_i)^2 = S_{yy} - \frac{S_{xy}^2}{S_{xx}}
$$

with $S_{yy} = \sum_{i=1}^n (y_i - \bar{y})^2$ (sample variance of y)
- **Confidence Interval (95%):**  
  Range where the **mean** predicted revenue is expected to lie with 95% confidence.

- **Prediction Interval (95%):**  
  Range where an **individual new value** is expected to fall with 95% certainty. It is wider than the confidence interval as it accounts for both model and observation error.

---

## Methodology

The methodology applied to fulfill US31 involved:

1. Loading the station data from a CSV file using Python (`pandas` library).
2. Filtering the dataset by user-specified **station** and **year**.
3. Calculating the **Pearson correlation coefficient** between each cargo type and revenue.
4. Selecting the cargo with the **strongest correlation** (absolute value) with revenue.
5. Fitting a **simple linear regression model** using `statsmodels`.
6. Using **December's cargo value**, applying a 10% increase to simulate January of the next year.
7. Predicting the revenue using the fitted model.
8. Computing both the **confidence interval** and the **prediction interval** for the result.
9. Displaying a regression summary and optionally visualizing the regression fit.

---

# US31 - Result Interpretation and Analysis

## Analysis and Interpretation of Results

The statistical analysis carried out for US31 aimed to identify which type of cargo most significantly influenced the revenue of a specific railway station in a given year. This involved calculating Pearson correlation coefficients between each cargo type and the revenue, selecting the cargo with the highest correlation, applying a linear regression model, and estimating future revenue based on a 10% increase in that cargo.

In the case of **Frankfurt Station** for the year **2023**, the analysis revealed:

- The cargo type most correlated with revenue was **Cattle**, with a correlation coefficient of **0.7231**
- A **linear regression** was applied between Cattle and revenue:
    - **Slope:** 0.7699
    - **Intercept:** 2566.24
    - **R²:** 0.5229, indicating that approximately 52% of the variance in revenue is explained by the variation in Cattle cargo volume
    - **Standard Error of the Slope:** 0.2326

Using this model, we estimated the expected revenue for **January 2024**, assuming a **10% increase** in the Cattle cargo volume recorded in **December 2023**:

- **Projected Cattle Cargo:** 1507.23 units
- **Predicted Revenue:** €3722.48
- **95% Confidence Interval:** (€3418.38, €4026.57)
- **95% Prediction Interval:** (€3154.87, €4280.22)

---

## Extrapolation to the Project Context

Within the context of the Integrative Project, these results can guide strategic decision-making related to freight prioritization and resource planning. Specifically, this analysis enables:

1. **Targeted Cargo Investment:** By identifying which cargo types correlate most with revenue, stations can prioritize operational improvements or upgrades for those commodities.

2. **Forecasting Revenue:** Estimating revenue based on projected cargo growth allows players to simulate realistic financial outcomes and assess risk.

3. **Adaptive Strategy:** Players can compare correlations across different stations and years to adjust their gameplay or simulation paths accordingly.

4. **Scenario Testing:** Incorporating confidence and prediction intervals encourages users to reason with uncertainty, mirroring real-world economic planning.

---

# Workload Distribution Among Team Members

Below is the distribution of 100% of the work among the four active members of the group:

- **1240934 – Francisco Vasconcelos**: 35%
- **1240935 – Gabriel Azevedo**: 20%
- **1240936 – Gonçalo Azevedo**: 20%
- **1240941 – Paulo Ferreira**: 25%

The fifth member of the group did not attend or contribute to the project.

---
