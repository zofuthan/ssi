package me.dao;

import me.entry.Person;

public interface IPersonDao {

	public Person getPerson(int id);
	
	public void savePerson(Person p);
	
	public void deletePerson(int id);
}
