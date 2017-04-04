class Feature
{
	String name;
	boolean value = true;

	public Feature(String name, boolean value)
	{
		this.name = name;
		this.value = value;
	}

	public boolean getValue()
	{
		return this.value;
	}

	public int getDifference(Feature f)
	{
		return this.value == f.getValue() ? 0 : 1;
	}

	@Override
	public String toString()
	{
		return "'" + this.name + "'\t-->\t" + (this.value ? "True " : "False");
	}
}