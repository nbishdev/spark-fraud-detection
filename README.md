# Fraud Detection
Fraudulent job advertising analysis and detection using Spark, PySpark and Python Machine Learning.

For all the experiments, we used the [[Real or Fake] : Fake Job Description Prediction](https://www.kaggle.com/shivamb/real-or-fake-fake-jobposting-prediction) dataset.

## Part 1: Simple statistics and knowledge extraction using Spark / Scala

### Software setup instructions

1. Choose the desired version of Apache Spark from [here](https://spark.apache.org/downloads.html)
    For this experimentation we used Spark v2.4.5 pre-built for Apache Hadoop 2.7<br>
    **Note:** This Spark version is only compatible with Java version 8
2. Download the chosen Apache Spark version and extract it to home directory
    ```console
    wget https://archive.apache.org/dist/spark/spark-2.4.5/spark-2.4.5-bin-hadoop2.7.tgz
    tar -xzvf ./spark-2.4.5-bin-hadoop2.7.tgz -C ~/
    ```
3. Add Apache Spark's binaries to PATH
    ```console
    echo 'export SPARK_HOME=~/spark-2.4.5-bin-hadoop2.7' >> ~/.bashrc
    echo 'export PATH=$SPARK_HOME/bin:$PATH' >> ~/.bashrc
    source ~/.bashrc
    ```
4. Install the compatible Java Runtime and verify installation (instructions for Ubuntu)
    ```console
    sudo apt-get install -y openjdk-8-jre
    which java
    ```
5. Test the proper execution of Spark shell
    ```console
    spark-shell
    ```

### Script execution

**Note:** The dataset must be present in some directory in the file system.

While in the Spark shell, use the following commands to execute the scala script.
```console
:load /absolute/path/to/the/script/SimpleStatistics.scala
SimpleStatistics.main(Array("/absolute/path/to/the/dataset/fake_job_postings.csv"))
```

The output of the script contains:
* The number of lines in the dataset
* The number of fake job postings in the dataset
* The number of real job postings in the dataset
* The top-10 most required education in fake job postings in the dataset
* The top-10 most required education in real job postings in the dataset

## Part 2: Data analysis using PySpark

### Software setup instructions

1. Follow all the software setup steps from Part 1
2. Add PySpark's binaries to PATH and other configurations to .bashrc
    ```console
    export PYTHONPATH=$SPARK_HOME/python:$PYTHONPATH
    export PYSPARK_DRIVER_PYTHON="jupyter"
    export PYSPARK_DRIVER_PYTHON_OPTS="notebook"
    export PYSPARK_PYTHON=python3
    source ~/.bashrc
    ```
3. Install requirements

### Script execution

**Note:** The dataset must be present in some directory in the file system.

To execute the script, launch a Jupyter server and load the notebook.

Make sure you adjust the path to the dataset in cell No. 3.

The notebook performs the following operations:
* Outputs the mean value, median value and standard deviation of the minimum and maximum salary in fake and real job postings
* Outputs boxplots and distplots of the minimum and maximum salaries
* Performs outlier detection using the 1.5 x IQR rule
* Removes outliers combining the aforementioned rule with custom bounds based on research
* Outputs bi-grams and tri-grams (words) in fake and real jon postings' description
