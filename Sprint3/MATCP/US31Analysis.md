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

The goal of this analysis was to identify which cargo type most significantly contributed to the revenue of a specific railway station in a given year. This was achieved by:

- Calculating Pearson correlation coefficients between each cargo type and the revenue.
- Selecting the cargo with the highest correlation.
- Applying a linear regression model to quantify the relationship.
- Using December’s cargo data to simulate a 10% increase in January of the following year.
- Estimating the expected revenue and reporting both the **95% confidence interval** and **95% prediction interval**.

For the station of **Hamburg** in the year **2020**, the following results were obtained:

-  **Selected Cargo**: Iron
-  **Correlation Coefficient (r)**: 0.9111
  > This indicates a very strong positive linear relationship between the amount of Iron transported and the station's revenue.

### Linear Regression Model

- **Slope**: 0.8716
- **Intercept**: 351.3873
- **Coefficient of Determination (R²)**: 0.8301
- **Standard Error of the Slope**: *Calculated internally by the model*
- **Residual Sum of Squares (SQE)**: 450132.83

### Forecast for January 2021

Assuming a **10% increase** in the volume of Iron from December 2020:

- **New Cargo Quantity**: 487.42 units
- **95% Confidence Interval**: (€635.55, €916.94)
- **95% Prediction Interval**: (€283.02, €1269.47)

> The prediction interval is wider than the confidence interval because it accounts for both the model’s uncertainty and the variability expected in future individual observations.

---

## Extrapolation to the Project Context

This analysis provides essential insights into how different types of cargo affect station profitability, enabling players and stakeholders in the simulation to:

1. **Optimize Freight Handling**: Focus investments and logistic planning on cargo types with high revenue impact.
2. **Simulate Financial Outcomes**: Project future revenues under hypothetical scenarios (e.g., increased cargo demand).
3. **Incorporate Risk Analysis**: Use confidence and prediction intervals to estimate uncertainty in revenue forecasts.
4. **Enhance Strategic Decisions**: Apply these insights to prioritize routes, upgrade stations, or schedule resources efficiently.

---

This approach helps bridge statistical modeling with gameplay mechanics, enriching the simulation’s economic realism and decision-making depth.


# Workload Distribution Among Team Members

Below is the distribution of 100% of the work among the four active members of the group:

- **1240934 – Francisco Vasconcelos**: 35%
- **1240935 – Gabriel Azevedo**: 20%
- **1240936 – Gonçalo Azevedo**: 20%
- **1240941 – Paulo Ferreira**: 25%

The fifth member of the group did not attend or contribute to the project.

---
